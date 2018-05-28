package com.springcloud.provider.controller;

import com.springcloud.provider.entity.User;
import com.springcloud.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-05-28 13:27
 **/
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	Registration registration;

	@GetMapping("/{id}")
	public User getUserById(@PathVariable Integer id){
		return userService.findById(id);
	}

	@GetMapping("/getIpAndPort")
	public String getIpAndPort(){
		return registration.getHost() + ":" + registration.getPort();
	}

}
