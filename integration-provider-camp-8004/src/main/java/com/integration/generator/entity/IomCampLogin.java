package com.integration.generator.entity;

import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCampLogin
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 登录实体类
*/
public class IomCampLogin {
    private String id;

    private String userId;

    private String czryDldm;

    private String czryMc;

    private String sessionId;

    private Date loginTime;

    private Date logoutTime;

    private Date cjsj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getCzryDldm() {
        return czryDldm;
    }

    public void setCzryDldm(String czryDldm) {
        this.czryDldm = czryDldm == null ? null : czryDldm.trim();
    }

    public String getCzryMc() {
        return czryMc;
    }

    public void setCzryMc(String czryMc) {
        this.czryMc = czryMc == null ? null : czryMc.trim();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId == null ? null : sessionId.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }
}