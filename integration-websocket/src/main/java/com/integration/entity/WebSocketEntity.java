package com.integration.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: integration
 * @description
 * @author: hlq
 * @create: 2020-03-06 09:13
 **/
public class WebSocketEntity {
    private String id;
    private String newsTime;
    private String newsInfo;
    private String newsType;
    private String receiverId;
    private String czryId;
    private String czryDm;
    private String czryMc;
    private String cjsj;

    public WebSocketEntity(String id, String newsTime, String newsInfo, String newsType,
                           String receiverId, String czryId, String czryDm, String czryMc){
        this.id = id;
        this.newsTime = newsTime;
        this.newsInfo = newsInfo;
        this.newsType = newsType;
        this.receiverId = receiverId;
        this.czryId = czryId;
        this.czryDm = czryDm;
        this.czryMc = czryMc;
        this.cjsj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    public String getNewsInfo() {
        return newsInfo;
    }

    public void setNewsInfo(String newsInfo) {
        this.newsInfo = newsInfo;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getCzryId() {
        return czryId;
    }

    public void setCzryId(String czryId) {
        this.czryId = czryId;
    }

    public String getCzryDm() {
        return czryDm;
    }

    public void setCzryDm(String czryDm) {
        this.czryDm = czryDm;
    }

    public String getCzryMc() {
        return czryMc;
    }

    public void setCzryMc(String czryMc) {
        this.czryMc = czryMc;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }
}
