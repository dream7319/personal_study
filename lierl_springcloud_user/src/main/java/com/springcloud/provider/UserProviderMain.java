package com.springcloud.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-05-28 11:15
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class UserProviderMain {
	public static void main(String[] args) {
		SpringApplication.run(UserProviderMain.class,args);
	}
}
