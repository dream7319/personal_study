package com.springboot.swagger.service;

import com.springboot.swagger.base.User;

import java.util.List;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/8 10:53
 */
public interface IUserService {
    User selectByPrimaryKey(Integer id);

    List<User> findByCondition(String name, String email, boolean hasConfirm);

    List<User> findAll();

    int update(User user);
}
