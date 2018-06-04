package com.lierl.kotlin.springboot.repository

import com.lierl.kotlin.springboot.entity.People
import org.springframework.data.repository.CrudRepository

interface PeopleRepository : CrudRepository<People, Long> {
    fun findByLastName(lastName: String): List<People>?
}