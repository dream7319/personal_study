package com.spring.zookeeper.thrift.client;

import com.spring.zookeeper.thrift.provider.ThriftServerAddressProvider;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * 连接池 thrift client
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 23:05
 */
public class ThriftClientPoolFactory extends BasePoolableObjectFactory<TServiceClient> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ThriftServerAddressProvider thriftServerAddressProvider;
    private TServiceClientFactory<TServiceClient> clientFactory;
    private PoolOperationCallBack callBack;

    public ThriftClientPoolFactory() {
    }

    public ThriftClientPoolFactory(ThriftServerAddressProvider thriftServerAddressProvider,
                                   TServiceClientFactory<TServiceClient> clientFactory) {
        this.thriftServerAddressProvider = thriftServerAddressProvider;
        this.clientFactory = clientFactory;
    }

    public ThriftClientPoolFactory(ThriftServerAddressProvider thriftServerAddressProvider,
                                   TServiceClientFactory<TServiceClient> clientFactory,
                                   PoolOperationCallBack callBack) {
        this.thriftServerAddressProvider = thriftServerAddressProvider;
        this.clientFactory = clientFactory;
        this.callBack = callBack;
    }

    public static interface PoolOperationCallBack{
        /**销毁client之前执行*/
        void destory(TServiceClient client);

        /**创建成功后执行*/
        void make(TServiceClient client);
    }

    @Override
    public void destroyObject(TServiceClient client) throws Exception {
        if(callBack!=null){
            callBack.destory(client);
        }
        logger.info("destroyObject:{}", client);
        TTransport pin = client.getInputProtocol().getTransport();
        pin.close();
        TTransport pout = client.getOutputProtocol().getTransport();
        pout.close();
    }

    @Override
    public void activateObject(TServiceClient obj) throws Exception {
    }

    @Override
    public void passivateObject(TServiceClient obj) throws Exception {
    }

    @Override
    public boolean validateObject(TServiceClient client) {
        TTransport pin = client.getInputProtocol().getTransport();
        logger.info("validateObject input:{}", pin.isOpen());
        TTransport pout = client.getOutputProtocol().getTransport();
        logger.info("validateObject output:{}", pout.isOpen());
        return pin.isOpen() && pout.isOpen();
    }

    @Override
    public TServiceClient makeObject() throws Exception {
        InetSocketAddress address = thriftServerAddressProvider.selector();
        if(address == null){
            new RuntimeException("No provider available for remote service");
        }

        TSocket tSocket = new TSocket(address.getHostName(),address.getPort());
        TTransport transport = new TFramedTransport(tSocket);
        TBinaryProtocol protocol = new TBinaryProtocol(transport);
        TServiceClient client = this.clientFactory.getClient(protocol);
        transport.open();
        if(callBack != null){
            callBack.make(client);
        }
        return client;
    }
}
