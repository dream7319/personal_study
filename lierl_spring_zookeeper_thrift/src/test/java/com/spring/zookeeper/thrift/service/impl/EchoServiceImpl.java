package com.spring.zookeeper.thrift.service.impl;

import com.spring.zookeeper.thrift.service.EchoService;
import org.apache.thrift.TException;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 20:01
 */
public class EchoServiceImpl implements EchoService.Iface {
    @Override
    public String echo(String msg) throws TException {
        System.out.println("-----------" + msg);
        return "server: "+ msg;
    }
}
