package com.integration.generator.entity;

import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiKpiClas
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 指标大类实体类
*/
public class IomCiKpiClas {
    private String id;

    private String name;

    private String cjrId;

    private Date cjsj;

    private String xgrId;

    private Date xgsj;

    private Integer yxbz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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