package com.lierl.webflux.entity;

import lombok.*;

import java.util.Date;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/5/26 19:48
 */
@Data
@AllArgsConstructor
public class User {
    String id;
    String name;
    String email;

//    @Setter(AccessLevel.NONE)//age属性忽略setter方法
//    Integer age;

//    @Getter(AccessLevel.NONE)//birthday 属性忽略getter方法
//    Date birthday;


}
