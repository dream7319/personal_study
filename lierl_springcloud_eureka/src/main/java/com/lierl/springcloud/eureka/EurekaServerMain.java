package com.lierl.springcloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/5/27 21:12
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerMain {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerMain.class,args);
    }
}
