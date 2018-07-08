package com.spring.thrift;

import com.spring.thrift.proxy.ThriftServerProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 17:45
 */
public class ThriftServerTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-applicationContext-server.xml");
        ThriftServerProxy thriftServerProxy = (ThriftServerProxy) context.getBean(ThriftServerProxy.class);
        thriftServerProxy.start();
    }
}
