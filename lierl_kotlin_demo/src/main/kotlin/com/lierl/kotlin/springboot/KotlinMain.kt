package com.lierl.kotlin.springboot

import com.lierl.kotlin.springboot.entity.People
import com.lierl.kotlin.springboot.service.PeopleService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import java.util.*


@SpringBootApplication
class KotlinMain {


    @Bean
    fun init(repository: PeopleService) = CommandLineRunner {
        val now = Date()
        repository.save(People(null, "Jason", "Chen", "Male", 28, now, now))
        repository.save(People(null, "Bluce", "Li", "Male", 32, now, now))
        repository.save(People(null, "Corey", "Chen", "Female", 20, now, now))

        for (people in repository.findAll()!!) {
            println(people.toString())
        }

        val customer = repository.findOne(1L)
        println(customer.toString())
        for (chen in repository.findByLastName("Chen")!!) {
            println(chen.toString())
        }
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(KotlinMain::class.java, *args)
}