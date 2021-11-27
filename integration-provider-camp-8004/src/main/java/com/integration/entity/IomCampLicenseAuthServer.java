package com.integration.entity;

import java.math.BigDecimal;
import java.util.Date;
/**
* @Package: com.integration.entity
* @ClassName: IomCampLicenseAuthServer
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 模块授权实体类
*/
public class IomCampLicenseAuthServer {
    private BigDecimal id;

    private BigDecimal authId;

    private String serverIp;

    private String serverCode;

    private String serverDes;

    private BigDecimal cjrId;

    private Date cjsj;

    private BigDecimal xgrId;

    private Date xgsj;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getAuthId() {
        return authId;
    }

    public void setAuthId(BigDecimal authId) {
        this.authId = authId;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp == null ? null : serverIp.trim();
    }

    public String getServerCode() {
        return serverCode;
    }

    public void setServerCode(String serverCode) {
        this.serverCode = serverCode == null ? null : serverCode.trim();
    }

    public String getServerDes() {
        return serverDes;
    }

    public void setServerDes(String serverDes) {
        this.serverDes = serverDes == null ? null : serverDes.trim();
    }

    public BigDecimal getCjrId() {
        return cjrId;
    }

    public void setCjrId(BigDecimal cjrId) {
        this.cjrId = cjrId;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public BigDecimal getXgrId() {
        return xgrId;
    }

    public void setXgrId(BigDecimal xgrId) {
        this.xgrId = xgrId;
    }

    public Date getXgsj() {
        return xgsj;
    }

    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }
}