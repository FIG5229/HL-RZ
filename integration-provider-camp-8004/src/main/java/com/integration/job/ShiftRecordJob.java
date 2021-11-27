package com.integration.job;

import com.alibaba.fastjson.JSONObject;
import com.integration.dao.UnsendNewsDao;
import com.integration.fegin.WebsocketService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ProjectName: integration
 * @Package: com.integration.job
 * @ClassName: ShiftRecordJob
 * @Author: ztl
 * @Date: 2021-05-06
 * @Version: 1.0
 * @description:交班记录，推送到未读消息后，可以通过定时任务推送到前端
 */
@Component
public class ShiftRecordJob implements Job {
    @Resource
    private UnsendNewsDao unsendNewsDao;
    @Autowired
   private WebsocketService websocketService;
    Logger log = LoggerFactory.getLogger(ShiftRecordJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        List<Map> unsendNewsList = unsendNewsDao.getUnSendNews();

        if (unsendNewsList!=null && unsendNewsList.size()>0){
            List<String> ids = unsendNewsList.stream().map(sys->sys.get("ID").toString()).collect(Collectors.toList());
            List<Map> parms = new ArrayList<>();
            unsendNewsList.forEach(lt -> {
                //调用websocket推送信息
                Map map = new HashMap();
                //封装发送时需要的信息
                map.put("newsInfo", lt.get("NEWS_INFO"));
                List users = new ArrayList();
                users.add(lt.get("RECEIVER_ID"));
                map.put("receiverId", users);
                map.put("newsType", "2");
                map.put("id", lt.get("ID"));
                map.put("czryId",lt.get("CZRY_ID"));
                map.put("czryDm",lt.get("CZRY_DM"));
                map.put("czryMc",lt.get("CZRY_MC"));
                map.put("newsTime",lt.get("NEWS_TIME"));
                parms.add(map);
            });
            if(parms.size() > 0){
                List<Map> successSendList = websocketService.sendAppointUser(JSONObject.toJSONString(parms));
                //修改发送成功的状态
                if(successSendList!=null && successSendList.size() > 0){
                    unsendNewsDao.deleteUnSendNews(ids);
                }
            }
        }

    }
}
