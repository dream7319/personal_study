package com.spring.zookeeper.thrift.proxy;

import com.spring.zookeeper.thrift.client.ThriftClientPoolFactory;
import com.spring.zookeeper.thrift.provider.ThriftServerAddressProvider;
import lombok.Setter;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 客户端代理工厂
 *
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 23:00
 */
public class ThriftServiceClientProxyFactory implements FactoryBean,InitializingBean,Cloneable {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Setter
    private Integer maxActive = 32;// 最大活跃连接数
    
    @Setter
    private Integer idleTime = 180000;// ms,default 3 min,链接空闲时间, -1,关闭空闲检测
    @Setter
    private ThriftServerAddressProvider serverAddressProvider;

    private Object proxyClient;
    private Class<?> objectClass;

    private GenericObjectPool<TServiceClient> pool;

    private ThriftClientPoolFactory.PoolOperationCallBack callback = new ThriftClientPoolFactory.PoolOperationCallBack() {

        @Override
        public void destory(TServiceClient client) {
            logger.info("destroy");
        }

        @Override
        public void make(TServiceClient client) {
            logger.info("create");
        }
    };


    @Override
    public Object getObject() throws Exception {
        return proxyClient;
    }

    @Override
    public Class<?> getObjectType() {
        return objectClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 加载Iface接口
        objectClass = classLoader.loadClass(serverAddressProvider.getServiceName() + "$Iface");
        // 加载Client.Factory类
        Class<TServiceClientFactory<TServiceClient>> fi = (Class<TServiceClientFactory<TServiceClient>>) classLoader.loadClass(serverAddressProvider.getServiceName() + "$Client$Factory");
        TServiceClientFactory<TServiceClient> clientFactory = fi.newInstance();

        ThriftClientPoolFactory clientPool = new ThriftClientPoolFactory(serverAddressProvider, clientFactory, callback);
        pool = new GenericObjectPool<TServiceClient>(clientPool, makePoolConfig());

        System.out.println(pool.borrowObject());
//        InvocationHandler handler = makeProxyHandler();//方式1
        InvocationHandler handler = makeProxyHandler();//方式2
        proxyClient = Proxy.newProxyInstance(classLoader, new Class[] { objectClass }, handler);
    }

    private InvocationHandler makeProxyHandler() throws Exception{
        ThriftServiceClientProxy handler = new ThriftServiceClientProxy(pool);
        return handler;
    }

    private GenericObjectPool.Config makePoolConfig() {
        GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
        poolConfig.maxActive = maxActive;
        poolConfig.maxIdle = 1;
        poolConfig.minIdle = 0;
        poolConfig.minEvictableIdleTimeMillis = idleTime;
        poolConfig.timeBetweenEvictionRunsMillis = idleTime * 2L;
        poolConfig.testOnBorrow = true;
        poolConfig.testOnReturn = false;
        poolConfig.testWhileIdle = false;
        return poolConfig;
    }

    public void close() {
        if(pool!=null){
            try {
                pool.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (serverAddressProvider != null) {
            serverAddressProvider.close();
        }
    }
}
