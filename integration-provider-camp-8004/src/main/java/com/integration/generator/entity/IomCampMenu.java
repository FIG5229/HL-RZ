package com.integration.generator.entity;

import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCampMenu
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 菜单实体类
*/
public class IomCampMenu {
    private String gncdDm;

    private String gncdMc;

    private String sjGncdDm;

    private String sjGncdMc;

    private Integer gnfl;

    private Integer gncdLevel;

    private Integer gncdType;

    private String gncdImg;

    private String gncdUrl;

    private Integer sort;

    private String cjrId;

    private Date cjsj;

    private String xgrId;

    private Date xgsj;

    private Integer yxbz;

    public String getGncdDm() {
        return gncdDm;
    }

    public void setGncdDm(String gncdDm) {
        this.gncdDm = gncdDm == null ? null : gncdDm.trim();
    }

    public String getGncdMc() {
        return gncdMc;
    }

    public void setGncdMc(String gncdMc) {
        this.gncdMc = gncdMc == null ? null : gncdMc.trim();
    }

    public String getSjGncdDm() {
        return sjGncdDm;
    }

    public void setSjGncdDm(String sjGncdDm) {
        this.sjGncdDm = sjGncdDm == null ? null : sjGncdDm.trim();
    }

    public String getSjGncdMc() {
        return sjGncdMc;
    }

    public void setSjGncdMc(String sjGncdMc) {
        this.sjGncdMc = sjGncdMc == null ? null : sjGncdMc.trim();
    }

    public Integer getGnfl() {
        return gnfl;
    }

    public void setGnfl(Integer gnfl) {
        this.gnfl = gnfl;
    }

    public Integer getGncdLevel() {
        return gncdLevel;
    }

    public void setGncdLevel(Integer gncdLevel) {
        this.gncdLevel = gncdLevel;
    }

    public Integer getGncdType() {
        return gncdType;
    }

    public void setGncdType(Integer gncdType) {
        this.gncdType = gncdType;
    }

    public String getGncdImg() {
        return gncdImg;
    }

    public void setGncdImg(String gncdImg) {
        this.gncdImg = gncdImg == null ? null : gncdImg.trim();
    }

    public String getGncdUrl() {
        return gncdUrl;
    }

    public void setGncdUrl(String gncdUrl) {
        this.gncdUrl = gncdUrl == null ? null : gncdUrl.trim();
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
}