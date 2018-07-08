package com.spring.zookeeper.thrift.rpc;

import com.spring.zookeeper.thrift.resolver.ThriftServerIpLocalNetworkResolver;
import com.spring.zookeeper.thrift.resolver.ThriftServerIpResolver;
import com.spring.zookeeper.thrift.zookeeper.ThriftServerAddressRegister;
import lombok.Setter;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Constructor;

/**
 * 服务端注册工厂
 *
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 21:27
 */
public class ThriftServiceServerFactory implements InitializingBean {

    /**服务注册本机端口*/
    @Setter
    private Integer port = 8299;
    /**优先级*/
    @Setter
    private Integer weight = 1;
    /**服务实现类*/
    @Setter
    private Object service;
    /**服务版本号*/
    @Setter
    private String version;

    private ServerThread serverThread;

    /**服务注册*/
    @Setter
    private ThriftServerAddressRegister thriftServerAddressRegister;

    /**解析本机IP*/
    private ThriftServerIpResolver thriftServerIpResolver;

    @Override
    public void afterPropertiesSet() throws Exception {
        if(thriftServerIpResolver == null){
            thriftServerIpResolver = new ThriftServerIpLocalNetworkResolver();
        }

        String serverIp = thriftServerIpResolver.getServerIp();
        
        if(StringUtils.isEmpty(serverIp)){
            throw new RuntimeException("can't find server ip...");
        }
        
        String hostname = serverIp + ":" + port + ":" + weight;
        Class<?> clazz = service.getClass();
        Class<?>[] interfaces = clazz.getInterfaces();
        if(interfaces.length == 0){
            throw new IllegalClassFormatException("service-class should implements Iface");
        }
        TProcessor processor = null;
        String serviceName = null;
        for (Class<?> inter : interfaces) {
            String simpleName = inter.getSimpleName();
            if(!"Iface".equals(simpleName)){
                continue;
            }

            serviceName= inter.getEnclosingClass().getName();
            String pname = serviceName + "$Processor";

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class<?> pClass = classLoader.loadClass(pname);
            if(!TProcessor.class.isAssignableFrom(pClass)){
                continue;
            }
            Constructor<?> constructor = pClass.getConstructor(inter);
            processor = (TProcessor) constructor.newInstance(service);
            break;
        }

        if(processor == null){
            throw new IllegalClassFormatException("service-class should implements Iface");
        }

        /**单独开启线程，因为serve方法是阻塞的*/
        serverThread = new ServerThread(processor,port);
        serverThread.start();
        //注册服务

        if(thriftServerAddressRegister != null){
            thriftServerAddressRegister.register(serviceName,version,hostname);
        }
    }

    class ServerThread extends Thread{

        private TServer server;

        public ServerThread(TProcessor processor, Integer port) throws Exception{
            TNonblockingServerSocket serverSocket = new TNonblockingServerSocket(port);
            TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverSocket);
            TProcessorFactory processorFactory = new TProcessorFactory(processor);
            tArgs.processorFactory(processorFactory);
            tArgs.transportFactory(new TFramedTransport.Factory());
            tArgs.protocolFactory(new TBinaryProtocol.Factory(true,true));
            server = new TThreadedSelectorServer(tArgs);
        }

        @Override
        public void run() {
            server.serve();
        }

        public void stopServer(){
            server.stop();
        }
    }

    public void close() {
        serverThread.stopServer();
    }
}
