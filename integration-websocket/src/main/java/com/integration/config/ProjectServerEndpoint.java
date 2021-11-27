package com.integration.config;

import com.alibaba.fastjson.JSONObject;
import com.integration.feign.RobotService;
import com.integration.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dell
 */
@Component
@ServerEndpoint("/websocket/project/{username}")
public class ProjectServerEndpoint {
    private static Logger log = LoggerFactory.getLogger(ProjectServerEndpoint.class);
    @OnOpen
    public void openSession(@PathParam("username") String username, Session session) {
        WebSocketUtils.PROJECT_SESSIONS.put(username, session);
        Map map = new HashMap();
        map.put("state", true);
        map.put("type", 0);
        map.put("message", "连接成功");
        WebSocketUtils.projectSendMessage(username, JSONObject.toJSONString(map));
        log.error("项目websocket连接成功");
    }
    @OnMessage
    public void onMessage(@PathParam("username") String username, String message) {

    }

    @OnClose
    public void onClose(@PathParam("username") String username, Session session) {
        //当前的Session 移除
        WebSocketUtils.PROJECT_SESSIONS.remove(username);
        try {
            session.close();
        } catch (IOException e) {
            log.error("项目websocket关闭时异常",e);
        }
    }
    @OnError
    public void onError(Session session, Throwable throwable) {
        try {
            session.close();
        } catch (IOException e) {
            log.error("项目websocket链接异常",e);
            e.printStackTrace();
        }
    }
}
