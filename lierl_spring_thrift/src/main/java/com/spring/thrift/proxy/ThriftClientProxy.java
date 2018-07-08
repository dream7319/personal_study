package com.spring.thrift.proxy;

import com.spring.thrift.pool.ConnectionManager;
import lombok.Data;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Constructor;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 17:18
 */
@Data
public class ThriftClientProxy {

    @Autowired
    private ConnectionManager connectionManager;

    public Object getClient(Class clazz){
        Object result = null;

        try {
            TTransport transport = connectionManager.getSocket();
            TProtocol protocol = new TBinaryProtocol(transport);
            Class client = Class.forName(clazz.getName()+"$Client");
            Constructor con = client.getConstructor(TProtocol.class);
            result = con.newInstance(protocol);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
