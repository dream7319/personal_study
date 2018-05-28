package com.springcloud.ribbon.controller;

import com.springcloud.ribbon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-05-28 16:36
 **/
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/{id}")
	public User getUserById(@PathVariable Integer id){
		return restTemplate.getForObject("http://lierl-springcloud-user/user/"+id, User.class);
	}

	@GetMapping("/getIpAndPort")
	public String getIpAndPort(){
		return restTemplate.getForObject("http://lierl-springcloud-user/user/getIpAndPort",String.class);
	}
}
