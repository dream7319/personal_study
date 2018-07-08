package com.spring.thrift.service;

import com.spring.thrift.api.UserRequest;
import com.spring.thrift.api.UserResponse;
import com.spring.thrift.api.UserService;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 17:32
 */
@Service
public class UserServiceImpl implements UserService.Iface {
    @Override
    public UserResponse userInfo(UserRequest request) throws TException {
        try{
            UserResponse urp=new UserResponse();
            if(request.id.equals("10000")){
                urp.setCode("0");
                Map<String,String> params= new HashMap<String,String>();
                params.put("name", "csy");
                urp.setParams(params);
            }
            System.out.println("接收参数是：id="+request.id);
            return urp;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
