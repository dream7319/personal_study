package com.spring.zookeeper.thrift.resolver;

/**
 *
 * 解析thrift-server 端IP地址，用于注册服务
 * 1、可以从一个物理机或者虚拟机的特殊文件中解析
 * 2、可以获取指定网卡序号的IP
 * 3、其他
 *
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 21:50
 */
public interface ThriftServerIpResolver {
    String getServerIp() throws Exception;

    void reset();

    /**当IP变更时，将会调用reset方法*/
    static interface IpRestCallBack{
        void rest(String newIp);
    }
}
