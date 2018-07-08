package com.springboot.swagger.service.impl;

import com.springboot.swagger.base.User;
import com.springboot.swagger.mapper.UserMapper;
import com.springboot.swagger.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/8 10:53
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> findByCondition(String name, String email, boolean hasConfirm) {
        return userMapper.selectByCondition(name,email,hasConfirm);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(User user) {
        return userMapper.updateByPrimaryKey(user);
    }
}
