package com.lierl.kotlin.controller

import com.lierl.kotlin.service.PeopleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/5/27 16:51
 */
@Controller
class PeopleController {

    @Autowired
    val peopleService: PeopleService? = null

    @GetMapping("/hello")
    @ResponseBody
    fun hello(@RequestParam("lastName") lastName: String): Any{
        val peoples = peopleService?.findByLastName(lastName)
        val map = HashMap<Any,Any>()
        map.put("hello", peoples!!)
        return map
    }
}