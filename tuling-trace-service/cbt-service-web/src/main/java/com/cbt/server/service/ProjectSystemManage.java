package com.cbt.server.service;

import com.cbt.server.dao.entity.ClientSession;
import com.cbt.server.service.entity.ClientSessionVo;

import java.util.List;

/**
 *
 * 项目系统管理
 * Created by tommy on 17/6/26.
 */
public interface ProjectSystemManage {
    /**
     * 获取当前在线
     * @param proId
     * @return
     */
    public List<ClientSessionVo> getActiveSessions(Integer proId);
}
