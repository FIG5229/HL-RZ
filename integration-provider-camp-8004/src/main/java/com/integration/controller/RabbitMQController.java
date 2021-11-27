package com.integration.controller;

import com.alibaba.fastjson.JSONArray;
import com.integration.utils.RabbitUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
* @Package: com.integration.controller
* @ClassName: RabbitMQController
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: RabbitMQ处理
*/
@RestController
public class RabbitMQController {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${queuePmv}")
    private String queuePmv;
    @Value("${queueEmv}")
    private String queueEmv;

    @PostMapping(value = "/pmvSender")
    public void mqTopic1(@RequestBody String json1) {
        JSONArray json = JSONArray.parseArray(json1);
        for (int i = 0; i < json.size(); i++) {
            Map m = (Map) json.get(i);
            m.put("DATA_DOURCE", "1");
        }
        try {
            RabbitUtil.findQueuesByName(queuePmv);
            rabbitTemplate.convertAndSend(queuePmv, json);
        } catch (Exception e) {
            RabbitUtil.addQueues(queuePmv);
            rabbitTemplate.convertAndSend(queuePmv, json);
        }
    }

    @PostMapping(value = "/emvSender")
    public void mqTopic2(@RequestBody String json1) {
        JSONArray json = JSONArray.parseArray(json1);
        for (int i = 0; i < json.size(); i++) {
            Map<String, String> m = (Map) json.get(i);
        }
        try {
            RabbitUtil.findQueuesByName(queuePmv);
            rabbitTemplate.convertAndSend(queueEmv, json);
        } catch (Exception e) {
            RabbitUtil.addQueues(queuePmv);
            rabbitTemplate.convertAndSend(queueEmv, json);
        }
    }
}
