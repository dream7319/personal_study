package com.spring.zookeeper.thrift.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 *
 * 基于网卡获取IP地址
 *
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 21:56
 */
public class ThriftServerIpLocalNetworkResolver implements ThriftServerIpResolver {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String serverIp;

    @Override
    public String getServerIp() throws Exception {
        if(!StringUtils.isEmpty(serverIp)){
            return serverIp;
        }

        /**一个主机有多个网络接口*/
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while(interfaces.hasMoreElements()){
            NetworkInterface networkInterface = interfaces.nextElement();
            /**每个网络接口，都会有多个 网络地址，比如一定会有lookback地址，会有siteLocal地址等，以及IPV4或者ipv6*/
            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while(addresses.hasMoreElements()){
                InetAddress address = addresses.nextElement();
                if(address instanceof Inet6Address){
                    continue;
                }

                if(address.isSiteLocalAddress() && !address.isLoopbackAddress()){
                    serverIp = address.getHostAddress();
                    logger.info("resolve server ip : " + serverIp);
                    continue;
                }

            }
        }
        return serverIp;
    }

    @Override
    public void reset() {
        serverIp = null;
    }
}
