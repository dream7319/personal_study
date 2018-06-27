package com.springcloud.provider.repository;

import com.springcloud.provider.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-05-28 11:20
 **/
public interface UserRepository extends CrudRepository<User,Integer> {
}
