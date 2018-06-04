package com.lierl.kotlin.springboot.controller

import com.lierl.kotlin.springboot.service.PeopleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class PeopleController {
    @Autowired
    val peopleService: PeopleService? = null

    @GetMapping(value = "/hello")
    @ResponseBody
    fun hello(@RequestParam(value = "lastName") lastName: String): Any {
        val peoples = peopleService?.findByLastName(lastName)
        val map = HashMap<Any, Any>()
        map.put("hello", peoples!!)
        return map
    }

}