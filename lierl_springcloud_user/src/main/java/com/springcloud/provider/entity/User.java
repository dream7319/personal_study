package com.springcloud.provider.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-05-28 11:18
 **/
@Data
@Entity
@Table(name = "user")
public class User {
	@Id
	Integer id;
	String name;
	Integer age;
}
