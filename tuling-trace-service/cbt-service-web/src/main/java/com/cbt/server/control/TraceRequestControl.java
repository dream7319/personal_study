package com.cbt.server.control;

import com.cbt.agent.trace.TraceNode;
import com.cbt.server.common.UtilJson;
import com.cbt.server.control.entity.RequestTableParam;
import com.cbt.server.control.entity.RequestSearchResult;
import com.cbt.server.dao.entity.Project;
import com.cbt.server.service.ClientService;
import com.cbt.server.service.NodeQueryService;
import com.cbt.server.service.entity.PageList;
import com.cbt.server.service.entity.SearchRequestParam;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description: Action 跟踪请求列表控制器<br/>
 *
 * @author tommy@cbt.com
 * @version 1.0
 * @date: 2017/1/18 11:39
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/trace")
public class TraceRequestControl {

    @Autowired
    NodeQueryService searchService;

    @Autowired
    ClientService clientService;

    /**
     * TODO(这里用一句话描述这个方法的作用). <br/>
     *
     * @author tommy@cbt.com
     * @date: 2016年8月4日 下午4:41:18
     * @version 1.0
     */
    @RequestMapping("/requests")
    public ModelAndView openRequestListView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/trace/requestTableView");
        // 查询IP参数保存至cookie当中
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("clientIps".equals(cookie.getName())) {
                    mv.addObject("clientIps", cookie.getValue());
                }
            }
        }
        return mv;
    }


    @RequestMapping("/requestTableContent")
    public ModelAndView getRequestListData(HttpServletRequest httpRequest, HttpServletResponse response, RequestTableParam param) {
        ModelAndView view = new ModelAndView("/trace/requestTableContent");
        param = param == null ? new RequestTableParam() : param;


        // 过滤IP 保存至cooke 当中
        Cookie cookie = new Cookie("clientIps", param.getClientIps() == null ? "" : param.getClientIps());
        cookie.setMaxAge(Integer.MAX_VALUE); // 永久有效
        response.addCookie(cookie);

        SearchRequestParam searchParam = new SearchRequestParam();

        if (param.getClientIps() != null && !param.getClientIps().trim().equals("")) {
            searchParam.setClientIps(param.getClientIps().split(","));
        }
        searchParam.setPageIndex(param.getPageIndex());
        searchParam.setPageSize(param.getPageSize());
        searchParam.setTimeBegin(System.currentTimeMillis() - param.getAfterSeconds() * 1000);
        if (StringUtils.hasText(param.getQueryWord()))
            searchParam.setQueryWord(param.getQueryWord());
        searchParam.setNodeType(param.getNodeType());
        // 默认查找 跟踪入口 Http Service
        if (searchParam.getQueryWord() == null && searchParam.getNodeType() == null) {
            searchParam.setNodeType("http");
        }
        Project project = clientService.getProject(param.getProjectKey());
        PageList<TraceNode> pageList = searchService.searchNodePage(project.getProId(), searchParam);

        List<RequestSearchResult> searchResult = new ArrayList<>(pageList.getElements().size());
        SimpleDateFormat df = new SimpleDateFormat("MM月dd日hh:mm:ss");
        for (TraceNode node : pageList.getElements()) {
            RequestSearchResult request = new RequestSearchResult();
            request.setTraceId(node.getTraceId());
            request.setRpcId(node.getRpcId());
            request.setClipentIp(node.getFromIp());
            request.setCreateTime(df.format(new Date(node.getBeginTime())));
            request.setTitle(node.getServicePath() + "#" + node.getServiceName());

            // 求异常
            if (node.getErrorMessage() != null) {
                String error = node.getErrorMessage().substring(0, Math.min(300, node.getErrorMessage().length() - 1));
                request.setErrorMessage(error);
            }
            request.setDescribe(buildDescribe(node));

            searchResult.add(request);
        }
        view.addObject("requestDatas", searchResult);

        Integer pageTotals = pageList.getTotalPage();
        Integer pageIndex = pageList.getPageIndex();
        Integer pageBegin = pageIndex - 5 < 1 ? 1 : pageIndex - 5;
        Integer pageEnd = pageBegin + 9 > pageTotals ? pageTotals : pageBegin + 9;
        pageBegin = pageEnd - 9 < 1 ? 1 : pageEnd - 9;
        RequestTableParam paramByPage = new RequestTableParam();
        BeanUtils.copyProperties(param, paramByPage);
        paramByPage.setPageIndex(null);
        view.addObject("queryParam", paramByPage.toHttpParams());
        view.addObject("totals", pageList.getTotalElements());
        view.addObject("pageTotals", pageTotals);
        view.addObject("pageIndex", pageIndex);
        view.addObject("pageBegin", pageBegin);
        view.addObject("pageEnd", pageEnd);
        view.addObject("projectKey",param.getProjectKey());
        return view;
    }

    private String buildDescribe(TraceNode node) {
        StringBuilder sbuilder = new StringBuilder();
        if (!StringUtils.hasText(node.getInParam())) {
        } else if ("http".equals(node.getNodeType())) {
            // UtilJson.parse(node.getInParam());
            Map<String, Object> map = UtilJson.parse(node.getInParam(), Map.class);
            for (Map.Entry<String, Object> m : map.entrySet()) {
                sbuilder.append("&");
                sbuilder.append(m.getKey());
                sbuilder.append("=");
                sbuilder.append(m.getValue());
            }
            if (map.size() > 0) {
                sbuilder.delete(0, 1);
            }

        } else if ("Data Base".equals(node.getNodeType())) {
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
                // sbuilder.append(SQLUtils.formatMySql(sql));
                sbuilder.append(sql.replaceAll("\n", ""));// 去指换行符
            }
        } else {
            String inparam = node.getInParam().substring(0, Math.min(300, node.getInParam().length() - 1));
            sbuilder.append(inparam + "...");
        }

        return sbuilder.toString();
    }

    private static Map<String, String> getCookieByNames(Cookie[] cookies, String... names) {
        Map<String, String> result = new HashMap<>();
        for (Cookie cookie : cookies) {
            if (ArrayUtils.indexOf(names, cookie.getName()) > -1) {
                result.put(cookie.getName(), cookie.getValue());
                if (result.size() == names.length) // 已全部获取
                    break;
            }
        }

        return result;
    }

}
