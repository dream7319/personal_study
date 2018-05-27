package com.lierl.kotlin.repository

import com.lierl.kotlin.entity.People
import org.springframework.data.repository.CrudRepository

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/5/27 16:42
 */
interface PeopleRepository : CrudRepository<People,Long>{
    fun findByLastName(lastName: String): List<People>?
}