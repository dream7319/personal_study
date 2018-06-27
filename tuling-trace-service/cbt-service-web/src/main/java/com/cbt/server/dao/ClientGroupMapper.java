package com.cbt.server.dao;

import com.cbt.server.dao.entity.ClientGroup;
import org.apache.ibatis.annotations.Param;

public interface ClientGroupMapper {
    int deleteByPrimaryKey(Integer clientId);

    int insert(ClientGroup record);

    int insertSelective(ClientGroup record);

    ClientGroup selectByPrimaryKey(Integer clientId);

    int updateByPrimaryKeySelective(ClientGroup record);

    int updateByPrimaryKey(ClientGroup record);

    ClientGroup selectMaster(@Param("platform") String platform);
}