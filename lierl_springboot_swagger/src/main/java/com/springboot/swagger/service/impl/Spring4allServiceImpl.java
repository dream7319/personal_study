package com.springboot.swagger.service.impl;

import com.springboot.swagger.base.Spring4all;
import com.springboot.swagger.mapper.Spring4allMapper;
import com.springboot.swagger.service.ISpring4allService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/8 11:26
 */
@Service
public class Spring4allServiceImpl implements ISpring4allService {

    @Autowired
    private Spring4allMapper spring4allMapper;

    @Override
    public Spring4all selectById(Integer id) {
        return spring4allMapper.selectByPrimaryKey(id);
    }
}
