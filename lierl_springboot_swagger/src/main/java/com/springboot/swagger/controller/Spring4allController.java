package com.springboot.swagger.controller;

import com.springboot.swagger.base.Spring4all;
import com.springboot.swagger.service.ISpring4allService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/8 11:27
 */
@RestController
@RequestMapping("/spring")
public class Spring4allController {

    @Resource
    private ISpring4allService spring4allService;

    @GetMapping("/{id}")
    public Spring4all findById(@PathVariable Integer id){
        return spring4allService.selectById(id);
    }
}
