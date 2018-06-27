package com.cbt.server.common;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by tommy on 16/10/16.
 */
public class UtilJson {
    public static String toJsonString(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static <T>T parse(String jsonString,Class<T> c) {
        return JSON.parseObject(jsonString,c);
    }
    public static final <T> List<T> parseArray(String text, Class<T> clazz) {
        return JSON.parseArray(text,clazz);
    }

}
