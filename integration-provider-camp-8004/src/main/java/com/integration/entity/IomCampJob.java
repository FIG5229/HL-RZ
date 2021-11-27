package com.integration.entity;

import java.util.Date;
/**
* @Package: com.integration.entity
* @ClassName: IomCampJob
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 定时任务
*/
public class IomCampJob {
    /**
     * 定时任务状态
     */
    public static enum jobstate{
        /**
         * 启动
         */
        running,
        /**
         * 停止
         */
        stopped
    }


    private Integer id;

    private String name;

    private String description;

    private String className;

    private String cron;

    private String jobState;

    private String cjrId;

    private Date cjsj;

    private String xgrId;

    private Date xgsj;

    private Integer yxbz;

    private String cjrName;

    private String cgrName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron == null ? null : cron.trim();
    }

    public String getJobState() {
        return jobState;
    }

    public void setJobState(String jobState) {
        this.jobState = jobState == null ? null : jobState.trim();
    }


    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
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

    public String getCjrId() {
        return cjrId;
    }

    public void setCjrId(String cjrId) {
        this.cjrId = cjrId;
    }

    public String getXgrId() {
        return xgrId;
    }

    public void setXgrId(String xgrId) {
        this.xgrId = xgrId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getCjrName() {
        return cjrName;
    }

    public void setCjrName(String cjrName) {
        this.cjrName = cjrName;
    }

    public String getCgrName() {
        return cgrName;
    }

    public void setCgrName(String cgrName) {
        this.cgrName = cgrName;
    }
}