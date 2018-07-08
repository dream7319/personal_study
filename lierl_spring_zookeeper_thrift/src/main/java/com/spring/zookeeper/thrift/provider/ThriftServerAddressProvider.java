package com.spring.zookeeper.thrift.provider;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 获取服务地址接口
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 20:43
 */
public interface ThriftServerAddressProvider {
    /**获取服务名称*/
    String getServiceName();

    /**
     * 获取所有服务端地址
     * @return
     */
    List<InetSocketAddress> findServerAddressList();

    /**
     * 获取一个address，可以随机获取，内部可以使用合适的算法
     * @return
     */
    InetSocketAddress selector();

    void close();
}
