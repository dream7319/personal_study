package com.lierl.kotlin.springboot.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class People(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long?,
        val firstName: String?,
        val lastName: String?,
        val gender: String?,
        val age: Int?,
        val gmtCreated: Date,
        val gmtModified: Date
){
    override fun toString(): String {
        return "People(id=$id, firstName='$firstName', lastName='$lastName', gender='$gender', age=$age, gmtCreated=$gmtCreated, gmtModified=$gmtModified)"
    }
}