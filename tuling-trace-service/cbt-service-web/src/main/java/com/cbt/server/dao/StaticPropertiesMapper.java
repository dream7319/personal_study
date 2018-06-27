package com.cbt.server.dao;

import com.cbt.server.dao.entity.StaticProperties;
import org.apache.ibatis.annotations.Param;

public interface StaticPropertiesMapper {
    int deleteByPrimaryKey(Integer staticId);

    int insert(StaticProperties record);

    int insertSelective(StaticProperties record);

    StaticProperties selectByPrimaryKey(Integer staticId);

    int updateByPrimaryKeySelective(StaticProperties record);

    int updateByPrimaryKeyWithBLOBs(StaticProperties record);

    int updateByPrimaryKey(StaticProperties record);
    StaticProperties selectByKey(@Param("key") String key);
}