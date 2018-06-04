package com.lierl.kotlin.springboot.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {
    @GetMapping(value = *arrayOf("/helloworld", "/"))
    fun helloworld(): Any {
        return "Hello,World!"
    }
}