package com.cbt.server.dao;

import com.cbt.server.dao.entity.ProjectApp;
import com.cbt.server.dao.entity.ProjectAppWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectAppMapper {
    int deleteByPrimaryKey(Integer appId);

    int insert(ProjectAppWithBLOBs record);

    int insertSelective(ProjectAppWithBLOBs record);

    ProjectAppWithBLOBs selectByPrimaryKey(Integer appId);

    int updateByPrimaryKeySelective(ProjectAppWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ProjectAppWithBLOBs record);

    int updateByPrimaryKey(ProjectApp record);

    List<ProjectApp> selectByProjectId(@Param("proId") Integer proId);
}