package com.spring.zookeeper.thrift.zookeeper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 注册服务列表到Zookeeper
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 20:09
 */
@NoArgsConstructor
@AllArgsConstructor
public class ThriftServerAddressRegisterZookeeper implements ThriftServerAddressRegister{

    private final Logger logger = LoggerFactory.getLogger(ThriftServerAddressRegisterZookeeper.class);

    @Setter
    private CuratorFramework zkClient;

    @Override
    public void register(String service, String version, String address) {
        if(zkClient.getState() == CuratorFrameworkState.LATENT){
            zkClient.start();
        }
        if(StringUtils.isEmpty(version)){
            version = "1.0.0";
        }

        /**创建临时节点*/
        try {
            zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                    .forPath("/" + service + "/" + version + "/" +address);
        } catch (Exception e) {
            logger.error("register service address to zookeeper exception:{}",e);
            throw new RuntimeException("register service address to zookeeper exception:{}", e);
        }
    }

    public void close(){
        if(zkClient != null){
            zkClient.close();
        }
    }
}
