package com.cbt.server.dao;

import com.cbt.server.dao.entity.Project;
import org.apache.ibatis.annotations.Param;

public interface ProjectMapper {
    int deleteByPrimaryKey(Integer proId);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Integer proId);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);

    /**
     * @param proKey
     * @return
     */
    Project selectByProjectKey(@Param("proKey") String proKey);
}