package com.integration.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@ServerEndpoint("/websocket/{username}")
public class ChatRoomServerEndpoint {
    @OnOpen
    public void openSession(@PathParam("username") String username, Session session) {
        WebSocketUtils.ONLINE_USER_SESSIONS.put(username, session);
        //0心跳，1日程，2交班 3一键巡检，4一键备份，5 应急启停，6数据备份，7日志备份

        //封装需要发送的消息
        Map map1 = new HashMap();
        map1.put("type", "0");
        Map map2 = new HashMap();
        map2.put("state", true);
        map1.put("receiverData", map2);
        String message = "[" + username + "] 客户端信息！";
//        WebSocketUtils.sendMessageAll("服务器连接成功！");
        WebSocketUtils.sendMessage(session, JSONObject.toJSONString(map1));
        System.out.println("连接成功" + message);
    }

    @OnMessage
    public void onMessage(@PathParam("username") String username, String message) {
//        System.out.println("服务器收到："+"[" + username + "] : " + message);
        //WebSocketUtils.sendMessageAll("[" + username + "] : " + message);
    }

    @OnClose
    public void onClose(@PathParam("username") String username, Session session) {
        //当前的Session 移除
        WebSocketUtils.ONLINE_USER_SESSIONS.remove(username);
        //并且通知其他人当前用户已经断开连接了
        //WebSocketUtils.sendMessageAll("[" + username + "] 断开连接！");
        try {
            session.close();
        } catch (IOException e) {
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        try {
            session.close();
        } catch (IOException e) {
        }
    }
}