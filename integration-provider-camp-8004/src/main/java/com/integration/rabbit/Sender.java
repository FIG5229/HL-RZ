package com.integration.rabbit;

import com.alibaba.fastjson.JSON;
import com.integration.entity.Domain;
import com.integration.entity.Org;
import com.integration.service.DomainService;
import com.integration.service.OrgService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.rabbit
 * @ClassName: Sender
 * @Author: ztl
 * @Date: 2021-07-07
 * @Version: 1.0
 * @description:给队列推送数据
 */
@Component
public class Sender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private DomainService domainService;

    @Autowired
    private OrgService orgService;
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 数据来源同步
     */
    public void sendDatasourceChgNotice() {
        Map<String, Object> doc = new HashMap<>();
        doc.put("isChange", true);
        amqpTemplate.convertAndSend("datasourceChgNoticeQueues", doc);
    }
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 数据域及组织机构同步
     */
    public void sendDomainAndOrgData(){
        Map<String, Object> doc = new HashMap<>();
        //获取所有数据域
        List<Domain> domainList = domainService.getAllDomainData();
        //获取所有组织机构数据
        List<Org> orgList = orgService.getAllOrgData();
        doc.put("domainList", domainList==null?null:JSON.toJSONString(domainList));
        doc.put("orgList",orgList==null?null:JSON.toJSONString(orgList));
        amqpTemplate.convertAndSend("domainAndOrgDataQueues", doc);
    }
}
