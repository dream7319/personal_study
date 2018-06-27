package com.cbt.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.cbt.server.CbtException;
import com.cbt.server.dao.*;
import com.cbt.server.dao.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cbt.server.CBT;
import com.cbt.server.common.UtilEncryption;
import com.cbt.server.model.ClientLoginParam;
import com.cbt.server.model.ClientSessionVo;
import com.cbt.server.model.UpdateLog;
import com.cbt.server.service.ClientService;
import org.springframework.util.Assert;

/**
 * 客户端登陆服务实现
 */
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ClientProjectBindingMapper bindingMapper;
    @Autowired
    private ClientGroupMapper clientMapper;
    @Autowired
    private ClientVersionMapper versionMapper;
    @Autowired
    private ClientSessionMapper sessionMapper;

    @Autowired
    private ProjectConfigMapper configMapper;

    @Autowired
    private  ProjectAppMapper appMapper;

    @Value("${downloadUrl}")
    private String downloadUrl;

    @Override
    public ClientSessionVo login(ClientLoginParam login, String sign) {
        // 参数验证
        CBT.hasText(login.getProKey(), "参数proKey为空");

        //   签名验证
        Project project = projectMapper.selectByProjectKey(login.getProKey());
        CBT.notNull(project, String.format("不存在指定项目 proKey=%s", login.getProKey()));
        checkLoginSign(project, login, sign);
        //  获取客户端版本信息
        ClientProjectBinding clientBind = bindingMapper.selectByProIdAndPlatform(project.getProId(), login.getPlatform());
        Integer clientId;
        if (clientBind != null) {
            clientId = clientBind.getClientId();
        } else {
            clientId = clientMapper.selectMaster(login.getPlatform()).getClientId();
        }

        //  逻辑删除旧会话
        sessionMapper.updateDisable(project.getProId(), login.getClientMacAddress(), login.getAppPath());

        ClientVersion clientVersion = versionMapper.selectClientMaxVersion(clientId);
        //  插入登陆会话信息
        ClientSession session = new ClientSession();
//        session.setAppId(login.getAppId());
        session.setAppPath(login.getAppPath());
        session.setCreateTime(new Date());
        session.setClientIp(login.getClientIp());
        session.setClientMacAddress(login.getClientMacAddress());
        session.setGatewayIp(""); // TODO 获取访问者网关IP
        session.setProId(project.getProId());
        session.setVersionId(clientVersion.getVersionId());
        session.setVersionName(clientVersion.getVersionName());
        session.setHeartbeat(1);
        sessionMapper.insertSelective(session);


        //  创建新的会话
        ClientSessionVo sessionVo = new ClientSessionVo();
        sessionVo.setSessionId(String.valueOf(session.getSessionId()));
        sessionVo.setAppId(login.getAppId());
        sessionVo.setClientMd5(clientVersion.getDataMd5());
        sessionVo.setClientUploadUrls(getUploadUrls(clientVersion));
        sessionVo.setClientVersion(clientVersion.getVersionName());
        sessionVo.setProKey(project.getProKey());
        sessionVo.setLoginTime(System.currentTimeMillis());

        // 加载项目及APP配置

        sessionVo.setConfigs(buildProjectStaticConfigs(project.getProId()));
        return sessionVo;
    }

    /**
     * 构建项目静态配置值
     * @return
     */
    private Properties buildProjectStaticConfigs(Integer proId){
        SessionConfigBuild build=new SessionConfigBuild();
        List<ProjectApp> apps = appMapper.selectByProjectId(proId);
        // 加载系统配置 proId= -1
        List<ProjectConfig> systemConfigs = configMapper.selectByProjectId(-1, null, false);
        // 加载项目配置
        List<ProjectConfig> projectConfigs = configMapper.selectByProjectId(proId, null, false);
        projectConfigs.addAll(systemConfigs);
        Integer appIds[]=new Integer[apps.size()];
        for (int i = 0; i < apps.size(); i++) {
            ProjectApp app=apps.get(i);
            appIds[i]=app.getAppId();
            build.setAppName(app.getAppId(),app.getAppName());
            build.setAppNamespace(app.getAppId(),app.getNamespace());

        }
        for (ProjectConfig config : projectConfigs) {
            if (config.getAppId() != null) {
                build.setAppConfig(config.getAppId(),config.getConfigKey(),config.getConfigValue());
            }else if(config.getProjectId()!=-1){
                build.setProConfig(config.getConfigKey(),config.getConfigValue());
            }else if(config.getProjectId()==-1){
                build.setSysConfig(config.getConfigKey(),config.getConfigValue());
            }
        }
        build.setAppIds(appIds);
        return build.toProperties();
    }

    private String[] getUploadUrls(ClientVersion clientVersion) {
        return new String[]{downloadUrl + "&key=" + clientVersion.getDataMd5()};
    }

    private void checkLoginSign(Project project, ClientLoginParam login, String sign) {
        String result = null;
        String secret = project.getProSecret();
        String sign1 = UtilEncryption.md5(secret + login.toString() + secret);
        CBT.isTrue(sign1.equals(sign), "密钥验证失败,请检查密钥 proSecret 配置是否正确");
    }

    // 必须登陆之后才可以下载
    @Override
    public byte[] download(String md5) {
        return versionMapper.selectByMd5(md5).getDataByte();
    }

    @Override
    public void addUpdateLog(UpdateLog log) {
        // TODO: 16/11/16 插入更新日志信息
    }

    /**
     * 根据Session ID 获取客户端会话
     *
     * @param sessionId
     * @return
     * @throws CbtException 当指定session 不存在的时候抛出此异常
     */
    @Override
    public ClientSession getSession(Integer sessionId) throws CbtException {
        ClientSession session = sessionMapper.selectByPrimaryKey(sessionId);
        if (session == null) {
            throw new CbtException(CBT.error_param, String.format("不存在ID=%s的会话", sessionId));
        }
        return session;
    }

    /**
     * 根据 项目key 获取项目信息
     *
     * @param proKey
     * @return
     * @throws CbtException 当指定项目不存在时抛出此异常
     */
    @Override
    public Project getProject(String proKey) throws CbtException {
        CBT.hasText(proKey, "参数'proKey'不能为空");
        Project pro = projectMapper.selectByProjectKey(proKey);
        CBT.notNull(pro, String.format("指定项目不存在  proKey=%s", proKey));
        return pro;
    }

    /***
     * 更新指定会话的心跳
     *
     * @param sessionId
     */
    @Override
    public void sendSessionEcho(Integer sessionId) {
        Assert.notNull(sessionId,"参数'sessionId' 不能为空");
        sessionMapper.clientEcho(sessionId);
    }

}
