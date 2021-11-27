package com.integration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * @ClassName WebsocketController
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2020/7/30 14:03
 * @Version 1.0
 **/
@Controller
public class WebsocketController {

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    //订阅路径为 /toAll/bulletScreen 推送给所有订阅此路径的
    //SendTo 发送至 Broker 下的指定订阅路径
    @SendTo("/toAll/bulletScreen")
    public String say(BulletMessageVO clientMessage,StompHeaderAccessor headerAccessor) {
        String result=null;
        if (clientMessage!=null){
            result=clientMessage.getUsername()+":"+clientMessage.getMessage();
        }
        return result+headerAccessor.getUser().getName();
    }

    // 指定人推送 此订阅链接为 /toSingle+用户+/demo 如：param.get("name")=lisi 则 订阅链接为 /toSingle/lisi/demo
    @MessageMapping("/chatToUser")
    public void chatToUser(Map<String,String> param, StompHeaderAccessor headerAccessor) {
        String formName = headerAccessor.getUser().getName();
        messagingTemplate.convertAndSendToUser(param.get("name"),"/demo","message from "+formName);
    }
}
