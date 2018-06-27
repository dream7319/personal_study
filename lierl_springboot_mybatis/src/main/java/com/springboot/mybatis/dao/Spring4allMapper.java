package com.springboot.mybatis.dao;

import com.springboot.mybatis.entity.Spring4all;
import java.util.List;

public interface Spring4allMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Spring4all record);

    Spring4all selectByPrimaryKey(Integer id);

    List<Spring4all> selectAll();

    int updateByPrimaryKey(Spring4all record);
}