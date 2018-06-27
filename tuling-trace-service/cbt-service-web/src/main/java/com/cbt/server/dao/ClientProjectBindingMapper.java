package com.cbt.server.dao;

import com.cbt.server.dao.entity.ClientProjectBinding;
import org.apache.ibatis.annotations.Param;

public interface ClientProjectBindingMapper {
    int deleteByPrimaryKey(Integer bindId);

    int insert(ClientProjectBinding record);

    int insertSelective(ClientProjectBinding record);

    ClientProjectBinding selectByPrimaryKey(Integer bindId);

    int updateByPrimaryKeySelective(ClientProjectBinding record);

    int updateByPrimaryKey(ClientProjectBinding record);

    ClientProjectBinding selectByProIdAndPlatform(@Param("proId") Integer proId, @Param("platform") String platform);
}