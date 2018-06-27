package com.cbt.server.control;

import com.alibaba.druid.sql.SQLUtils;
import com.cbt.agent.trace.TraceNode;
import com.cbt.server.CBT;
import com.cbt.server.common.ForMatJSONStr;
import com.cbt.server.common.UtilJson;
import com.cbt.server.control.entity.GoNode;
import com.cbt.server.control.entity.UserSession;
import com.cbt.server.service.NodeQueryService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cbt.server.control.entity.GoNode.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description: 跟踪控制台<br/>
 *
 * @author tommy@cbt.com
 * @version 1.0
 * @date: 2017/2/4 10:45
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/trace")
public class TraceConsoleControl extends BaseControl {

    @Autowired
    NodeQueryService nodeQueryService;

    @RequestMapping("console")
    public ModelAndView openConsolePage(HttpServletRequest request, String traceId){
        CBT.hasText(traceId,"参数'traceId'不能为空");
        ModelAndView modelAndView=new ModelAndView("/trace/traceConsoleView");
        modelAndView.addObject("traceId",traceId);
        return modelAndView;
    }

    @RequestMapping("getFlowNodeData")
    @ResponseBody
    public GoNode getflowChartNodeData(HttpServletRequest request, String traceId) {
        CBT.hasText(traceId, "参数 'traceId' 不能为空");
//        GoNode result = new ArrayList<>();
        UserSession session = getSessionInfo(request);
        List<TraceNode> traceNodes = nodeQueryService.getNodesByTraceId(session.getCurrentProjectId(), traceId);
        return convertGoNode(traceNodes);
    }

    @RequestMapping("getDetailInfo")
    public ModelAndView getDetailInfo(HttpServletRequest request,String traceId,String nodeId){
        ModelAndView result=new ModelAndView("/trace/traceConsoleDetailContent");
        UserSession session = getSessionInfo(request);
        TraceNode node  = nodeQueryService.getNodeById(session.getCurrentProjectId(),traceId,nodeId);
        result.addObject("node",node);

        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
        result.addObject("invoke_time", format.format(new Date(node.getBeginTime())));
        if ("Data Base".equals(node.getNodeType())) {
           /* if (node.getInParam() != null) {
                String[] params = UtilJson.parse(node.getInParam(), String[].class);
                String sql = null;
                for (int i = 0; i < params.length; i++) {
                    if (i == 0)
                        sql = params[0];
                    else if (sql != null && sql.indexOf("?") > -1)
                        sql = sql.replaceFirst("\\?", "'" + params[i] + "'");
                }
                if (sql != null) {
                    // 格式化sql 语句
                    result.addObject("sql_text", SQLUtils.formatMySql(sql));
                } else {
                    result.addObject("sql_text", "error sql is empty");
                }
            }
            if(node.getOutParam()!=null){
                String[][] resultSet = UtilJson.parse(node.getOutParam(),
                        String[][].class);
                result.addObject("resultSet", resultSet);
            }
            result.setViewName("/trace/SqlDetailsView");*/
        } else {
            if (node.getInParam() != null) {
                if (node.getInParam().trim().startsWith("{") || node.getInParam().trim().startsWith("["))
                    result.addObject("in_param", ForMatJSONStr.format(node.getInParam()));
                else
                    result.addObject("in_param", node.getInParam());
            }
            if (node.getOutParam() != null) {
                if (node.getOutParam().trim().startsWith("{") || node.getInParam().trim().startsWith("["))
                    result.addObject("out_param", ForMatJSONStr.format(node.getOutParam()));
                else
                    result.addObject("out_param", node.getOutParam());
            }
            //result.setViewName("/trace/NormalDetailsView");
        }

        return result;
    }

    // TODO: 2017/2/4 转换TraceNode至goNode
    private GoNode convertGoNode(List<TraceNode> traceNodes) {
        GoNode goNode = new GoNode();
        goNode.setCopiesArrayObjects(true);
        goNode.setCopiesArrays(true);
        List<GoLinkData> linkDataArray = new ArrayList<>(traceNodes.size());
        List<GoNodeData> nodeDataArray = new ArrayList<>(traceNodes.size());
        GoLinkData linkData;
        GoNodeData nodeData;
        String parentId;
        String nodeId;

        for (TraceNode node : traceNodes) {
            nodeData = new GoNodeData(node.getRpcId(),"yellowgrad",node.getAppDetail());
            nodeDataArray.add(nodeData);

            // 0 表示为web服务根节点
            if (!node.getRpcId().equals("0")) {
                nodeId = node.getRpcId();
                parentId = nodeId.substring(0, nodeId.lastIndexOf("."));
                linkData = new GoLinkData(parentId, node.getRpcId(), node.getNodeType());
                linkDataArray.add(linkData);
            }


        }
        goNode.setNodeDataArray(nodeDataArray.toArray(new GoNodeData[nodeDataArray.size()]));
        goNode.setLinkDataArray(linkDataArray.toArray(new GoLinkData[linkDataArray.size()]));

        return goNode;
    }
}
