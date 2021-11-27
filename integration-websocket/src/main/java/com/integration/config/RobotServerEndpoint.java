package com.integration.config;

import com.alibaba.fastjson.JSONObject;
import com.integration.feign.RobotService;
import com.integration.utils.SpringContextUtil;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@ServerEndpoint("/websocket/robot/{username}")
public class RobotServerEndpoint {

    @OnOpen
    public void openSession(@PathParam("username") String username, Session session) {
        WebSocketUtils.ROBOT_SESSIONS.put(username, session);
        //0心跳，1日程，2交班 3一键巡检，4一键备份，5 应急启停，6数据备份，7日志备份

        //封装需要发送的消息
       // Map map1 = new HashMap();
       // map1.put("type", "0");
       // Map map2 = new HashMap();
       // map2.put("state", true);
       // map1.put("receiverData", map2);
        //String message = "[" + username + "] 客户端信息！";
       // WebSocketUtils.sendMessage(session, JSONObject.toJSONString(map1));
        Map map = new HashMap();
        map.put("state", true);
        map.put("type", 0);
        map.put("message", "连接成功");
        WebSocketUtils.robotSendMessage(username, JSONObject.toJSONString(map));
        System.out.println("连接成功");
    }
    @OnMessage
    public void onMessage(@PathParam("username") String username, String message) {

        if(!message.equals("0")){

            System.out.println("接到消息"+message+"用户"+username);
            RobotService robotService = SpringContextUtil.getBean(RobotService.class);
            //调用消息添加方法
            try {
                robotService.receiveMessage(message);
            }catch (Exception e){
            	e.printStackTrace();
                System.out.println("添加消息异常");
            }

        }


    }

    @OnClose
    public void onClose(@PathParam("username") String username, Session session) {

        System.out.println("连接中断");
        RobotService robotService = SpringContextUtil.getBean(RobotService.class);

        //关闭连接时修改当前人的所在位置为0
        robotService.updatePersonnelOnGroupState(username,"0");
        //当前的Session 移除
        WebSocketUtils.ROBOT_SESSIONS.remove(username);
        try {
            session.close();
        } catch (IOException e) {
        }
    }
    @OnError
    public void onError(Session session, Throwable throwable) {

        throwable.printStackTrace();

        System.out.println("连接异常");
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
