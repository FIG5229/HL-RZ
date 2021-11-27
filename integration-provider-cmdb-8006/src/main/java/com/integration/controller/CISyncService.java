package com.integration.controller;

import com.alibaba.fastjson.JSONObject;
import com.integration.entity.PageResult;
import com.integration.service.TypeDataService;
import com.integration.utils.RedisUtils;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CISyncController
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2021/8/23 10:50
 * @Version 1.0
 **/
@Component
public class CISyncService {

    private static final Logger logger = LoggerFactory.getLogger(CISyncService.class);


    @Autowired
    private TypeDataService typeDataService;

    /**
     * 监听接口平台获取告警数据
     * @param data
     * @param deliveryTag MQ消息唯一标识 ID
     * @param channel MQ连接通道
     */
    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("ci.sync"), autoStartup= "true", id = "Message")
    public void  saveEmvAlarm(@Payload List<Map<String,Object>> data , @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {
        logger.info("接收CI数量：" + data.size());

        PageResult pg = null;
        try{
            pg = typeDataService.updateCI(data);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("处理ci同步数据出错！", e);
        }

        //无论成功失败，都确认消息，防止反复消费
        //手动确认rabbitMQ消息，第二个参数标志是否批量确认消息
        channel.basicAck(deliveryTag,false);
        /*if(pg != null && pg.isReturnBoolean()){

        }else{

            //处理失败
            channel.basicReject(deliveryTag,true);
        }*/

    }

}
