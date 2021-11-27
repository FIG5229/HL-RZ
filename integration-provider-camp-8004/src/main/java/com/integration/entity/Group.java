package com.integration.entity;

import com.integration.mybatis.entity.Page;

/**
* @Package: com.integration.entity
* @ClassName: Group
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 分组
*/
public class Group {
    private String czry_mc;
    private String startTime;
    private String endTime;
    private Page page;

    public String getCzry_mc() {
        return czry_mc;
    }

    public void setCzry_mc(String czry_mc) {
        this.czry_mc = czry_mc;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
