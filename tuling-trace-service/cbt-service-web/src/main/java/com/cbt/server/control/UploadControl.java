package com.cbt.server.control;

import com.cbt.server.CBT;
import com.cbt.server.CbtException;
import com.cbt.server.dao.entity.ClientSession;
import com.cbt.server.service.ClientService;
import com.cbt.server.service.NodeStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description: 节点上传中心控制器<br/>
 *
 * @author tommy@cbt.com
 * @version 1.0
 * @date: 2017/1/18 11:36
 * @since JDK 1.7
 */
@RestController
@RequestMapping("upload")
public class UploadControl extends BaseControl {

    static final Logger logger = LoggerFactory.getLogger(UploadControl.class);

    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @Autowired
    ClientService clientService;

    @Autowired
    NodeStoreService storeService;

    /**
     * 跟踪节点上传方法
     *
     * @param sessionId 会话ID
     * @param sign      会话签名
     * @param nodeJson  节点信息
     */
    @RequestMapping(params = "type=TraceNode")
    @ResponseBody
    public String uploadNode(String sessionId, String sign, final String[] nodeJson) {
        CBT.hasText(sessionId, "参数'sessionId' 不能为空");
//        CBT.hasText(sign, "参数'sign' 不能为空");
        CBT.notNull(nodeJson, "参数'nodeJson' 不能为空");
        CBT.notEmpty(nodeJson, "参数'nodeJson' 不能存在空项");
        CBT.isTrue(nodeJson.length>0,"参数'nodeJson'数量不能为空");

        final String[] nodes;
        // 最后一个为空项，解决nodeJson 数量为1时参数错乱的问题
        if(nodeJson[nodeJson.length-1].trim().equals("")){
            nodes=  Arrays.copyOfRange(nodeJson,0,nodeJson.length-1);
        }else{
            nodes=nodeJson;
        }

        Integer sId;
        try {
            sId = Integer.parseInt(sessionId);
        } catch (NumberFormatException e) {
            throw new CbtException(CBT.error_param, "参数'sessionId' 必须为数字!实际为:" + sessionId);
        }
        final ClientSession session = clientService.getSession(sId);

        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    storeService.saveTraceNode(session.getProId(), nodes);
                    logger.info(String.format("成功增加%s个跟踪节点。会话id=%s,项目id=%s。",nodeJson.length,session.getSessionId(),session.getProId()));
                } catch (Exception e) {
                    if (logger.isDebugEnabled()) {
                        logger.error(String.format(String.format("节点存储失败！会话id=%s  项目id=%s,节点数量=%s,内容=%s", session.getSessionId(), session.getProId(), nodeJson.length, Arrays.toString(nodeJson))), e);
                    } else {
                        logger.error(String.format(String.format("节点存储失败！会话id=%s 项目id=%s,节点数量=%s", session.getSessionId(), session.getProId(), nodeJson.length)), e);
                    }
                }
            }
        });
        return "已成功接收节点数据";
    }
}
