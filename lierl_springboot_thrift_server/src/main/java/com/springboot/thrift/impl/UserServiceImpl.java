package com.springboot.thrift.impl;

import com.springboot.thrift.service.UserDto;
import com.springboot.thrift.service.UserService;
import org.apache.thrift.TException;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 14:49
 */
public class UserServiceImpl implements UserService.Iface {
    @Override
    public UserDto getUser() throws TException {
        return new UserDto(1000,"lierl");
    }
}
