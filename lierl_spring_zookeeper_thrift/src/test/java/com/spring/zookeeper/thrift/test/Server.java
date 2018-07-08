package com.spring.zookeeper.thrift.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 20:01
 */
public class Server {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new
                ClassPathXmlApplicationContext("classpath:spring-zookeeper-thrift-server.xml");
        context.start();
        System.out.println("服务已启动");
    }
}
