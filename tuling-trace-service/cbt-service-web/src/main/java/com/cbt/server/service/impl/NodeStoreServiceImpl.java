package com.cbt.server.service.impl;

import com.cbt.agent.trace.TraceNode;
import com.cbt.server.CBT;
import com.cbt.server.common.UtilJson;
import com.cbt.server.service.NodeStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Description: TODO 一句话描述类是干什么的<br/>
 *
 * @author tommy@cbt.com
 * @version 1.0
 * @date: 2017/1/18 16:18
 * @since JDK 1.7
 */
@Service
public class NodeStoreServiceImpl implements NodeStoreService {
    @Autowired
    private ElasticsearchTemplate template;


    @Override
    public void saveTraceNode(Integer proId, String[] nodeJsons) {
        Assert.notNull(proId);
        Assert.notNull(nodeJsons);
        Assert.notEmpty(nodeJsons);
        for (String nodeJson : nodeJsons) {
            storeToEs(proId, UtilJson.parse(nodeJson, TraceNode.class), nodeJson);
        }
    }

    private void storeToEs(Integer proId, TraceNode node, String nodeJson) {
        if (node.getNodeType().equals("dubbo-provider"))
            return;
        IndexQuery query = new IndexQueryBuilder().
                withIndexName(CBT.es_index_prefix + proId)
                .withType("TraceNode").
                        withId(node.getTraceId() + "_" + node.getRpcId())
                .withSource(nodeJson)
                .build();
        template.index(query);
    }
}
