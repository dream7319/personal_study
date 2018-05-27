package com.lierl.dubbo.service.impl;

import com.lierl.dubbo.service.DemoService;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/5/13 18:43
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return name;
    }
}
