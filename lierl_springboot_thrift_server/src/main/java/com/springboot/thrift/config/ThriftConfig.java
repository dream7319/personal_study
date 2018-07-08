package com.springboot.thrift.config;

import com.springboot.thrift.impl.UserServiceImpl;
import com.springboot.thrift.service.UserService;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 14:35
 */
@Configuration
public class ThriftConfig {
    //设置核心池大小
    int corePoolSize=5;
    //设置线程池最大能承受多少线程
    int maximumPoolSize=100;
    //当线程池数大于corePoolSize、小于maxiumPoolSize时，超出corePoolSize的线程数的生命周期
    long keepActiveTime=200;

    //设置线程池缓存队列的排队策略为FIFO，并且指定缓存队列大小为5
    BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(5);

    ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepActiveTime,TimeUnit.MILLISECONDS,workQueue);

    @PostConstruct
    public void init(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                tServer().serve();
            }
        });
    }

    @Bean
    public TServerTransport tServerTransport(){
        try {
            return new TServerSocket(7911);
        } catch (TTransportException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public TServer tServer(){
        //发布服务
        UserService.Processor processor = new UserService.Processor(new UserServiceImpl());

        TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(tServerTransport()).processor(processor));

        return server;
    }

}
