package com.cbt.server.control.entity;

/**
 * Description:请求参数<br/>
 *
 * @author tommy@cbt.com
 * @version 1.0
 * @date: 2017年1月20日 下午14:45:43
 * @since JDK 1.7
 */
public class RequestTableParam {
    Integer pageSize = 15; // 指定每页大小时
    Integer pageIndex = 1; // 指定页
    String clientIps; // ip过滤,多个用逗号隔开
    String queryWord;
    Long afterSeconds = 3600L; // 节点创建时间在指定秒数之内.默认在1小时之内
    String nodeType;
    private String projectKey;// 项目key

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getClientIps() {
        return clientIps;
    }

    public void setClientIps(String clientIps) {
        this.clientIps = clientIps;
    }

    public long getAfterSeconds() {
        return afterSeconds;
    }

    public void setAfterSeconds(long afterSeconds) {
        this.afterSeconds = afterSeconds;
    }

    public String getQueryWord() {
        return queryWord;
    }

    public void setQueryWord(String queryWord) {
        this.queryWord = queryWord;
    }

    public void setAfterSeconds(Long afterSeconds) {
        this.afterSeconds = afterSeconds;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String toHttpParams() {
        StringBuilder builder = new StringBuilder();
        if (pageSize != null) {
            builder.append("&pageSize=");
            builder.append(pageSize);
        }
        if (pageIndex != null) {
            builder.append("&pageIndex=");
            builder.append(pageIndex);
        }
        if (clientIps != null) {
            builder.append("&clientIps=");
            builder.append(clientIps);
        }
        if (queryWord != null) {
            builder.append("&queryWord=");
            builder.append(queryWord);
        }
        if (afterSeconds != null) {
            builder.append("&afterSeconds=");
            builder.append(afterSeconds);
        }
        if (nodeType != null) {
            builder.append("&nodeType=");
            builder.append(nodeType);
        }
        if (projectKey != null) {
            builder.append("&projectKey=");
            builder.append(projectKey);
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(0);
        }
        return builder.toString();

    }
}