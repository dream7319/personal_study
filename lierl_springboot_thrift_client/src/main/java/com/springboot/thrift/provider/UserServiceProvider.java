package com.springboot.thrift.provider;

import com.springboot.thrift.config.ZooKeeperConfig;
import com.springboot.thrift.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 15:28
 */
@Component
public class UserServiceProvider {

    public UserService.Client getBlanceUserService(){
        Map<String, UserService.Client> serviceMap =ZooKeeperConfig.serviceMap;
        //以负载均衡的方式获取服务实例
        for (Map.Entry<String, UserService.Client> entry : serviceMap.entrySet()) {
            System.out.println("可供选择服务:"+entry.getKey());
        }
        int rand=new Random().nextInt(serviceMap.size());
        String[] mkeys = serviceMap.keySet().toArray(new String[serviceMap.size()]);
        return serviceMap.get(mkeys[rand]);
    }
}
