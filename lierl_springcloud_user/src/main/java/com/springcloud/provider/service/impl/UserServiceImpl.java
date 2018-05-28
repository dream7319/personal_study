package com.springcloud.provider.service.impl;

import com.springcloud.provider.entity.User;
import com.springcloud.provider.repository.UserRepository;
import com.springcloud.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-05-28 11:19
 **/
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;


	@Override
	public User findById(Integer id) {
		return userRepository.findOne(id);
	}
}
