package com.integration.config;

import com.alibaba.fastjson.JSONObject;
import com.integration.dao.WebSocketDao;
import com.integration.entity.WebSocketEntity;
import com.integration.utils.SeqUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: integration
 * @description
 * @author: hlq
 * @create: 2020-03-04 17:17
 **/
@RestController
@RequestMapping(value = "/websocketSend")
public class WebsocketSend {

    private static Logger log = LoggerFactory.getLogger(WebsocketSend.class);
    public static final Map<String, Session> ONLINE_USER_SESSIONS = new ConcurrentHashMap<>();
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Resource
    private WebSocketDao webSocketDao;

    /**
     * 向指定用户推送消息
     * 返回推送成功的
     * @param content
     * @return
     */

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/sendAppointUser", method = RequestMethod.POST)
    public List<Map> sendAppointUser(String content) throws Exception{
        List<Map> result = new ArrayList<>();

        List<Map> datas = JSONObject.parseObject(content,List.class);

        datas.forEach(data -> {

            List users = (List)data.get("receiverId");
            List<WebSocketEntity> webSocketEntitys = new ArrayList<>();

            Map resultMap = new HashMap();//返回的map
            List resultList = new ArrayList();//返回的list

            //循环每个发送的人，逐个发送
            users.forEach(lt ->{
                try{
//                    messagingTemplate.convertAndSendToUser(lt.toString(), "/msg", new TextMessage( data.get("newsInfo").toString()));
                    WebSocketUtils.sendMessage(lt.toString(), data.get("newsInfo").toString());
                    //轮询类业务，返回发送成功的数据
                    if(data.get("newsType").toString().equals("1")){
                        resultList.add(lt.toString());
                    }
                    Session session = WebSocketUtils.ONLINE_USER_SESSIONS.get(lt.toString());
                    if(session == null || !session.isOpen()){
                        if(data.get("newsType").toString().equals("2")){
                            webSocketEntitys.add(new WebSocketEntity(SeqUtil.getId(),data.get("newsTime").toString(),
                                    data.get("newsInfo").toString(),data.get("newsType").toString(),lt.toString(),
                                    data.get("czryId").toString(),data.get("czryDm").toString(),data.get("czryMc").toString()));
                        }
                    }
                }catch (Exception e){
                    if(data.get("newsType").toString().equals("2")){
                        webSocketEntitys.add(new WebSocketEntity(SeqUtil.getId(),data.get("newsTime").toString(),
                                data.get("newsInfo").toString(),data.get("newsType").toString(),lt.toString(),
                                data.get("czryId").toString(),data.get("czryDm").toString(),data.get("czryMc").toString()));
                    }
                    log.error("发送信息出现异常！参数：{},{}",lt.toString(),new TextMessage( data.get("newsInfo").toString()));
                    log.error("发送信息出现异常！",e);
                }

            });

            //及时提醒业务，存入未发送表
            if(data.get("newsType").toString().equals("2")){
                if(webSocketEntitys.size() > 0){
                    webSocketDao.deleteByReceiverId(users);
                    webSocketDao.inssetUnSends(webSocketEntitys);
                }
                resultMap.put("result", true);
                result.add(resultMap);
            }else{//轮询业务,封装发送成功的
                if(resultList.size() > 0){
                    resultMap.put("newsInfo", data.get("newsInfo").toString());
                    resultMap.put("id", data.get("id").toString());
                    resultMap.put("receiverId", resultList);
                    result.add(resultMap);
                }
            }
        });
        return result;
    }

    @RequestMapping(value = "/sendAllUser", method = RequestMethod.POST)
    public void sendAllUser(String content){
        List<Map> datas = JSONObject.parseObject(content,List.class);
        datas.forEach(data -> {
//            messagingTemplate.convertAndSend(new TextMessage( data.get("newsInfo").toString()));
            WebSocketUtils.sendMessageAll(data.get("newsInfo").toString());
        });
    }

    /**
     * 维秘推送单用户
     * @param content
     * @throws Exception
     */
    @RequestMapping(value = "/sendRobotToUser", method = RequestMethod.POST)
    public void sendRobotToUser(@RequestParam String receiveId,@RequestParam String content) throws Exception{
    	if(WebSocketUtils.ROBOT_SESSIONS.containsKey(receiveId)) {
    		WebSocketUtils.robotSendMessage(receiveId,content);
    	}
    }

    /**
     * 维秘推送全部用户
     * @param content
     * @throws Exception
     */
    @RequestMapping(value = "/sendRobotToAllUser", method = RequestMethod.POST)
    public void sendRobotToAllUser(String content) throws Exception{
        WebSocketUtils.robotSendMessageAll(content);
    }

    /**
     * 项目推送单用户
     * @param content
     * @throws Exception
     */
    @RequestMapping(value = "/sendProjectToUser", method = RequestMethod.POST)
    public void sendProjectToUser(@RequestParam String userId,@RequestParam String content) throws Exception{
        if(WebSocketUtils.ROBOT_SESSIONS.containsKey(userId)) {
            WebSocketUtils.projectSendMessage(userId,content);
        }
    }
    /**
     * 维秘推送全部用户
     * @param content
     * @throws Exception
     */
    @RequestMapping(value = "/sendProjectToAllUser", method = RequestMethod.POST)
    public void sendProjectToAllUser(String content) throws Exception{

        WebSocketUtils.projectSendMessageAll(content);

    }
}
