package com.springcloud.feign.controller;

import com.springcloud.feign.client.UserClient;
import com.springcloud.feign.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-05-28 15:27
 **/
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserClient userClient;

	@GetMapping("/{id}")
	public User getUserById(@PathVariable Integer id){
		return userClient.getUserById(id);
	}

	@GetMapping("/getIpAndPort")
	public String getIpAndPort(){
		return userClient.getIpAndPort();
	}
}
