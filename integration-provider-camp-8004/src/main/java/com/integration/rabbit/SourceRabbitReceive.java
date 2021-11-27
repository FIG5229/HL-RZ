package com.integration.rabbit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.integration.entity.Domain;
import com.integration.entity.Org;
import com.integration.service.DictService;
import com.integration.service.DomainService;
import com.integration.service.OrgService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration-provider-camp-8004
 * @Package: com.integration.rabbit
 * @ClassName: SourceRabbitReceive
 * @Author: ztl
 * @Date: 2021-07-09
 * @Version: 1.0
 * @description:事件来源信息
 */
@Component
public class SourceRabbitReceive {
    private static final Logger logger = LoggerFactory.getLogger(SourceRabbitReceive.class);
    @Autowired
    private DictService dictService;
    @Autowired
    private DomainService domainService;
    @Autowired
    private OrgService orgService;
    @Value("${mqQueues.isEnableDomainAndOrg:false}")
    private boolean isEnable;
    /**
     * 监听事件来源信息
     * @param data
     */
    @RabbitListener(queuesToDeclare = @Queue("dataSourceSyncQueues"))
    public void  saveSource(List<Map> data){
        try {
            //处理事件来源数据
            dictService.handleSourceData(data);
        } catch (Exception e) {
            logger.error("事件来源数据时报错：" + e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 数据域及组织机构数据同步监听
     */
    @RabbitListener(queuesToDeclare = @Queue("domainAndOrgDataSynQueues"))
    public void domainAndOrgData(@Payload Map data, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel){
        try{
            if (!isEnable){
                channel.basicReject(deliveryTag,true);
                return;
            }
            if (data.get("domainList")!=null){
                List<Domain> domainList = JSONArray.parseArray(data.get("domainList").toString(), Domain.class);
                domainService.handleDomainData(domainList);
            }
            if (data.get("orgList")!=null){
                List<Org> orgList = JSONArray.parseArray(data.get("orgList").toString(), Org.class);
                orgService.handleOrgData(orgList);
            }


        }catch (Exception e){
            try {
                //手动确认rabbitMQ消息，第二个参数标志是否批量确认消息
                channel.basicReject(deliveryTag,true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            logger.error("数据域及组织机构数据同步监听错误",e);
        }finally {
            try {
                //手动确认rabbitMQ消息，第二个参数标志是否批量确认消息
                channel.basicAck(deliveryTag,false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
