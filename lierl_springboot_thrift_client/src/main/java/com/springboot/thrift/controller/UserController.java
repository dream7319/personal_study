package com.springboot.thrift.controller;

import com.alibaba.fastjson.JSON;
import com.springboot.thrift.provider.UserServiceProvider;
import com.springboot.thrift.service.UserDto;
import com.springboot.thrift.service.UserService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/7 15:29
 */
@Controller
public class UserController {
    @Autowired
    UserServiceProvider userServiceProvider;

    @GetMapping("/hello")
    @ResponseBody
    String hello() throws TException{
        UserService.Client svr = userServiceProvider.getBlanceUserService();
        UserDto user = svr.getUser();
        return JSON.toJSONString(user);
    }
}
