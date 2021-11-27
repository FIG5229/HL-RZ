package com.integration.rabbit;

import com.integration.service.TypeItemService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
    private TypeItemService typeItemService;
    public void sendCiChgNotice(String sourceCiId, String ciTypeId) {
        Map<String, String> doc = new HashMap<>();
        doc.put("sourceCiId", sourceCiId);
        doc.put("ciTypeId", ciTypeId);
        amqpTemplate.convertAndSend("ciChgNoticeQueues", doc);
    }
    /**
     * @Author: ztl
     * date: 2021-08-12
     * @description: 推送大类及类定义数据
     */
    public void sendCiTypeAndItemData() {
        Map<String, Object> doc = typeItemService.getAllCiTypeAndItem();
        amqpTemplate.convertAndSend("ciTypeAndItemDataQueues", doc);
    }

}
