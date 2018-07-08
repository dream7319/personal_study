package com.spring.zookeeper.thrift.zookeeper;

import lombok.Setter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.StringUtils;

/**
 * 获取zookeeper 客户端连接
 *
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 18:54
 */

public class ZookeeperFactory implements FactoryBean<CuratorFramework> {
    @Setter
    private String zkHosts;
    /**session超时*/
    @Setter
    private int sessionTimeout = 30000;
    @Setter
    private int connectionTimeout = 30000;

    /**共享一个zk连接*/
    @Setter
    private boolean singleton = true;
    @Setter
    private CuratorFramework zkClient;
    /**全局path前缀，常用来区分不同的应用*/
    @Setter
    private String namespace;

    public final static String ROOT="thrift";


    @Override
    public CuratorFramework getObject() throws Exception {
        if(singleton){
            if(zkClient == null){
                zkClient = create();
                zkClient.start();
            }
            return zkClient;
        }
        return create();
    }

    private CuratorFramework create() throws Exception{
        if(StringUtils.isEmpty(namespace)){
            namespace = ROOT;
        }else{
            namespace = ROOT + "/" + namespace;
        }
        return create(zkHosts,sessionTimeout,connectionTimeout,namespace);
    }

    private CuratorFramework create(String zkHosts, int sessionTimeout, int connectionTimeout, String namespace) {
        return CuratorFrameworkFactory.builder()
                    .connectString(zkHosts)
                    .sessionTimeoutMs(sessionTimeout)
                    .connectionTimeoutMs(connectionTimeout)
                    .canBeReadOnly(true)
                    .namespace(namespace)
                    .retryPolicy(new ExponentialBackoffRetry(1000,Integer.MAX_VALUE))
                    .defaultData(null)
                    .build();
    }

    @Override
    public Class<?> getObjectType() {
        return CuratorFramework.class;
    }

    @Override
    public boolean isSingleton() {
        return singleton;
    }

    public void close(){
        if(zkClient != null){
            zkClient.close();
        }
    }
}
