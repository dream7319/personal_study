package com.cbt.server.dao;

import com.cbt.server.dao.entity.ClientUpdateLog;

public interface ClientUpdateLogMapper {
    int deleteByPrimaryKey(Integer logId);

    int insert(ClientUpdateLog record);

    int insertSelective(ClientUpdateLog record);

    ClientUpdateLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(ClientUpdateLog record);

    int updateByPrimaryKey(ClientUpdateLog record);
}