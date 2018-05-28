package com.springcloud.provider.service;

import com.springcloud.provider.entity.User;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-05-28 11:19
 **/
public interface UserService {
	User findById(Integer id);
}
