package com.lierl.kotlin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/5/27 16:38
 */
@SpringBootApplication
open class KotlinMain

fun main(args: Array<String>){
    SpringApplication.run(KotlinMain::class.java, *args)
}
