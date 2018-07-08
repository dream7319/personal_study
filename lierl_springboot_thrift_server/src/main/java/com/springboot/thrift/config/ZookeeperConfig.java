package com.springboot.thrift.config;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 14:53
 */
@Configuration
public class ZookeeperConfig {
    @Value("${service.name}")
    String serviceName;
    @Value("${zookeeper.server.list}")
    String serverList;

    ExecutorService executor = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void init(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                registerService();
                try {
                    TimeUnit.SECONDS.sleep(60*60*24*360*10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 注册服务
     * @return
     */
    public ZkClient registerService(){
        String servicePath = "/"+serviceName;//根节点路径
        ZkClient zkClient = new ZkClient(serverList);

        boolean rootExists = zkClient.exists(servicePath);
        if(!rootExists){
            zkClient.createPersistent(servicePath);
        }

        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        String ip = addr.getHostAddress().toString();
        String serviceInstance = System.nanoTime()+"-"+ip;
        //注册当前服务
        String currentService = servicePath+"/"+serviceInstance;
        zkClient.createEphemeral(currentService);

        System.out.println("提供的服务为："+ currentService);
        return zkClient;
    }
}
