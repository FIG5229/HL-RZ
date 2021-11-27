package com.integration.generator.entity;

import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCampMenuRes
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 菜单管理
*/
public class IomCampMenuRes {
    private String resDm;

    private String resMc;

    private String gncdDm;

    private Integer resType;

    private Integer sort;

    private String cjrId;

    private Date cjsj;

    private String xgrId;

    private Date xgsj;

    private Integer yxbz;

    private String resUrl;

    public String getResDm() {
        return resDm;
    }

    public void setResDm(String resDm) {
        this.resDm = resDm == null ? null : resDm.trim();
    }

    public String getResMc() {
        return resMc;
    }

    public void setResMc(String resMc) {
        this.resMc = resMc == null ? null : resMc.trim();
    }

    public String getGncdDm() {
        return gncdDm;
    }

    public void setGncdDm(String gncdDm) {
        this.gncdDm = gncdDm == null ? null : gncdDm.trim();
    }

    public Integer getResType() {
        return resType;
    }

    public void setResType(Integer resType) {
        this.resType = resType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCjrId() {
        return cjrId;
    }

    public void setCjrId(String cjrId) {
        this.cjrId = cjrId == null ? null : cjrId.trim();
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getXgrId() {
        return xgrId;
    }

    public void setXgrId(String xgrId) {
        this.xgrId = xgrId == null ? null : xgrId.trim();
    }

    public Date getXgsj() {
        return xgsj;
    }

    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }

    public Integer getYxbz() {
        return yxbz;
    }

    public void setYxbz(Integer yxbz) {
        this.yxbz = yxbz;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl == null ? null : resUrl.trim();
    }
}