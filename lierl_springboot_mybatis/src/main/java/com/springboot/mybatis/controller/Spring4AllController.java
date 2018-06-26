package com.springboot.mybatis.controller;

import com.springboot.mybatis.base.ResultBean;
import com.springboot.mybatis.service.Spring4AllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-06-26 10:57
 **/
@RestController
@RequestMapping("/spring")
public class Spring4AllController {

	@Autowired
	private Spring4AllService spring4AllService;

	@GetMapping("/all")
	public ResultBean<Object> getAll(){
		return new ResultBean<>(spring4AllService.getAll());
	}

	@GetMapping("test")
	public String test(){
		return "hello world";
	}
}
