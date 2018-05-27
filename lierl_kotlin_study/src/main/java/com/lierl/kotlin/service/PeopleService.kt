package com.lierl.kotlin.service

import com.lierl.kotlin.entity.People
import com.lierl.kotlin.repository.PeopleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/5/27 16:44
 */
@Service
open class PeopleService : PeopleRepository{

    @Autowired
    val peopleRepository: PeopleRepository? = null

    override fun existsById(id: Long?): Boolean {
        return peopleRepository?.existsById(id)!!
    }

    override fun deleteById(id: Long?) {
        return peopleRepository?.deleteById(id)!!
    }

    override fun <S : People?> save(entity: S): S {
        return peopleRepository?.save(entity)!!
    }

    override fun findAll(): MutableIterable<People> {
        return peopleRepository?.findAll()!!
    }

    override fun count(): Long {
        return peopleRepository?.count()!!
    }

    override fun findById(id: Long?): Optional<People> {
        return peopleRepository?.findById(id)!!
    }

    override fun deleteAll(entities: MutableIterable<People>?) {
        return peopleRepository?.deleteAll(entities)!!
    }

    override fun deleteAll() {
        return peopleRepository?.deleteAll()!!
    }

    override fun <S : People?> saveAll(entities: MutableIterable<S>?): MutableIterable<S> {
        return peopleRepository?.saveAll(entities)!!
    }

    override fun findAllById(ids: MutableIterable<Long>?): MutableIterable<People> {
        return peopleRepository?.findAllById(ids)!!
    }

    override fun delete(entity: People?) {
        return peopleRepository?.delete(entity)!!
    }

    override fun findByLastName(lastName: String): List<People>? {
        return peopleRepository?.findByLastName(lastName)
    }
}