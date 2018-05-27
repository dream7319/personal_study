package com.lierl.webflux.controller;

import com.lierl.webflux.entity.User;
import com.lierl.webflux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/5/26 20:00
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Flux<User> list(){
        return userService.list();
    }

    @GetMapping("/{id}")
    public Mono<User> getById(@PathVariable("id") String id){
        return userService.getById(id);
    }

    @PostMapping("")
    public Flux<User> create(@RequestBody Flux<User> users){
        return userService.createOrUpdate(users);
    }

    @PutMapping("/{id}")
    public Mono<User> update(@PathVariable("id") String id,
                             @RequestBody User user){
        Objects.requireNonNull(user);
        user.setId(id);
        return userService.createOrUpdate(user);
    }

    @DeleteMapping("/{id}")
    public Mono<User> delete(@PathVariable("id") String id){
        return userService.delete(id);
    }

}
