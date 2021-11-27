package com.integration.config;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName WebSocketUtils
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2020/9/18 15:38
 * @Version 1.0
 **/
public class WebSocketUtils {

    /**
     * webSocket 一键巡检功能容器
     */
    public static final Map<String, Session> ONLINE_USER_SESSIONS = new ConcurrentHashMap<>();

    /**
     * webSocket 维秘功能容器
     */
    public static final Map<String, Session> ROBOT_SESSIONS = new ConcurrentHashMap<>();
    /**
     * 项目功能容器
     */
    public static final Map<String, Session> PROJECT_SESSIONS = new ConcurrentHashMap<>();
    // 推送消息
    public static void sendMessage(Session session, String message) {
        if (session == null) {
            return;
        }
        synchronized(session) {
        	final RemoteEndpoint.Basic basic = session.getBasicRemote();
            if (basic == null)
            {
                return;
            }
            try {
                basic.sendText(message);
            } catch (IOException e) {
                System.out.println("sendMessage IOException "+ e);
            }
        }
        
    }
    // 全用户推送
    public static void sendMessageAll(String message) {
        ONLINE_USER_SESSIONS.forEach((sessionId, session) -> sendMessage(session, message));
    }

    // 单用户推送
    public static void sendMessage(String username, String message) {
        Session session = ONLINE_USER_SESSIONS.get(username);
        if(session != null && session.isOpen()){
            sendMessage(session, message);
        }
    }

    // 维秘全用户推送
    public static void robotSendMessageAll(String message) {
        ROBOT_SESSIONS.forEach((sessionId, session) -> sendMessage(session, message));
    }

    // 维秘单用户推送
    public static void robotSendMessage(String username, String message) {
        Session session = ROBOT_SESSIONS.get(username);
        if(session != null && session.isOpen()){
            sendMessage(session, message);
        }
    }

    // 项目单用户推送
    public static void projectSendMessage(String username, String message) {
        Session session = PROJECT_SESSIONS.get(username);
        if(session != null && session.isOpen()){
            sendMessage(session, message);
        }
    }
    // 维秘全用户推送
    public static void projectSendMessageAll(String message) {
        PROJECT_SESSIONS.forEach((sessionId, session) -> sendMessage(session, message));
    }
}
