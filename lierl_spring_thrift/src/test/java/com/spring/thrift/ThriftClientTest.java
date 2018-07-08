package com.spring.thrift;

import com.alibaba.fastjson.JSON;
import com.spring.thrift.api.UserRequest;
import com.spring.thrift.api.UserResponse;
import com.spring.thrift.api.UserService;
import com.spring.thrift.proxy.ThriftClientProxy;
import org.apache.thrift.TException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 17:46
 */
public class ThriftClientTest {
    public static void main(String[] args) throws TException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-applicationContext-client.xml");

        ThriftClientProxy thriftClientProxy = (ThriftClientProxy) context.getBean(ThriftClientProxy.class);
        UserService.Iface client = (UserService.Iface)thriftClientProxy.getClient(UserService.class);

        UserRequest request = new UserRequest();
        request.setId("10000");
        UserResponse urp = client.userInfo(request);
        System.out.println(JSON.toJSONString(urp));
    }
}
