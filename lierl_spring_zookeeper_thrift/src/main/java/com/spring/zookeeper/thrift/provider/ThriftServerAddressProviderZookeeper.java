package com.spring.zookeeper.thrift.provider;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * 使用zookeeper作为"config"中心,使用apache-curator方法库来简化zookeeper开发
 *
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 20:45
 */
@NoArgsConstructor
public class ThriftServerAddressProviderZookeeper implements ThriftServerAddressProvider,InitializingBean,Cloneable {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**注册的服务*/
    @Setter
    private String service;
    /**服务版本号*/
    @Setter
    private String version = "1.0.0";
    /**zk客户端*/
    @Setter
    private CuratorFramework zkClient;
    /**避免zk还没有连接上，就去调用服务*/
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private PathChildrenCache cachePath;

    // 用来保存当前provider所接触过的地址记录，当zookeeper集群故障时,可以使用trace中地址,作为"备份"
    private Set<String> trace = new HashSet<String>();

    private final List<InetSocketAddress> container = new ArrayList<InetSocketAddress>();

    private Queue<InetSocketAddress> inner = new LinkedList<InetSocketAddress>();

    private Object lock = new Object();

    // 默认权重
    private static final Integer DEFAULT_WEIGHT = 1;

    public ThriftServerAddressProviderZookeeper(CuratorFramework zkClient) {
        this.zkClient = zkClient;
    }


    @Override
    public String getServiceName() {
        return service;
    }

    @Override
    public List<InetSocketAddress> findServerAddressList() {
        return Collections.unmodifiableList(container);
    }

    @Override
    public synchronized InetSocketAddress selector() {
        if(inner.isEmpty()){
            if(!container.isEmpty()){
                inner.addAll(container);
            }else if(!trace.isEmpty()){
                synchronized (lock){
                    for (String hostname : trace) {
                        container.addAll(transfer(hostname));
                    }
                    Collections.shuffle(container);
                    inner.addAll(container);
                }
            }
        }
        InetSocketAddress isa = inner.poll();
        System.out.println("----------------" + isa.getPort());
        return isa;
    }

    @Override
    public void close() {
        try {
            if(cachePath != null){
                cachePath.close();
            }
            if(zkClient!=null){
                zkClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        /**如果zk没有启动，则启动*/
        if(zkClient.getState() == CuratorFrameworkState.LATENT){
            zkClient.start();
        }

        buildPathChildrenCache(zkClient,getServicePath(),true);
        cachePath.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        countDownLatch.await();
    }

    private String getServicePath() {
        return "/" + service + "/" + version;
    }

    private void buildPathChildrenCache(CuratorFramework zkClient, String servicePath, boolean cacheData) {
        cachePath = new PathChildrenCache(zkClient,servicePath,cacheData);

        cachePath.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws
                    Exception {
                PathChildrenCacheEvent.Type type = event.getType();
                switch (type) {
                    case CONNECTION_RECONNECTED:
                        logger.info("Connection is reconection.");
                        break;
                    case CONNECTION_SUSPENDED:
                        logger.info("Connection is suspended.");
                        break;
                    case CONNECTION_LOST:
                        logger.warn("Connection error,waiting...");
                        return;
                    case INITIALIZED:
                        logger.warn("Connection init ...");
                    default:
                }
                // 任何节点的时机数据变动,都会rebuild,此处为一个"简单的"做法.
                cachePath.rebuild();
                rebuild();
                countDownLatch.countDown();
            }

            protected void rebuild() throws Exception {
                List<ChildData> children = cachePath.getCurrentData();
                if(children == null || children.isEmpty()){
                    // 有可能所有的thrift server都与zookeeper断开了链接
                    // 但是,有可能,thrift client与thrift server之间的网络是良好的
                    // 因此此处是否需要清空container,是需要多方面考虑的.
                    container.clear();
                    logger.error("thrift server-cluster error...");
                    return;
                }

                List<InetSocketAddress> current = new ArrayList<InetSocketAddress>();
                String path = null;
                for (ChildData data : children) {
                    path = data.getPath();
                    logger.debug("get path: "+ path);
                    path = path.substring(getServicePath().length()+1);
                    logger.debug("get serviceAddress:"+path);
                    String address = new String(path.getBytes(), "utf-8");
                    current.addAll(transfer(address));
                    trace.add(address);
                }
                Collections.shuffle(current);
                synchronized (lock) {
                    container.clear();
                    container.addAll(current);
                    inner.clear();
                    inner.addAll(current);
                }
            }
        });
    }

    private Collection<? extends InetSocketAddress> transfer(String address) {
        String[] hostname = address.split(":");
        Integer weight = DEFAULT_WEIGHT;
        if(hostname.length == 3){
            weight = Integer.valueOf(hostname[2]);
        }

        String ip = hostname[0];
        Integer port = Integer.valueOf(hostname[1]);
        List<InetSocketAddress> result = new ArrayList<InetSocketAddress>();
        //根据优先级，将ip:port 添加多次到地址集中，随机获取地址实现负载
        for (Integer i = 0; i < weight; i++) {
            result.add(new InetSocketAddress(ip,port));
        }
        return result;
    }
}
