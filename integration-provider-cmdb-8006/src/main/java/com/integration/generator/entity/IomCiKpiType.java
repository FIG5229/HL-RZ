package com.integration.generator.entity;

import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiKpiType
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 指标大类关系实体类
*/
public class IomCiKpiType {
    private String id;

    private String kpiId;

    private Integer objType;

    private String objId;

    private Date cjsj;

    private Date xgsj;

    private Integer yxbz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getKpiId() {
        return kpiId;
    }

    public void setKpiId(String kpiId) {
        this.kpiId = kpiId == null ? null : kpiId.trim();
    }

    public Integer getObjType() {
        return objType;
    }

    public void setObjType(Integer objType) {
        this.objType = objType;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId == null ? null : objId.trim();
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
}