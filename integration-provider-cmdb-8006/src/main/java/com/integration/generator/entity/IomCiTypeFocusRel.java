package com.integration.generator.entity;

import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiTypeFocusRel
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class IomCiTypeFocusRel {
    private String id;

    private String typeId;

    private String sourceTypeId;

    private String targetTypeId;

    private String rltId;

    private String cjrId;

    private Date cjsj;

    private String xgrId;

    private Date xgsj;

    private Integer yxbz;

    /**
     * 数据域ID
     */
    private String domainId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getSourceTypeId() {
        return sourceTypeId;
    }

    public void setSourceTypeId(String sourceTypeId) {
        this.sourceTypeId = sourceTypeId == null ? null : sourceTypeId.trim();
    }

    public String getTargetTypeId() {
        return targetTypeId;
    }

    public void setTargetTypeId(String targetTypeId) {
        this.targetTypeId = targetTypeId == null ? null : targetTypeId.trim();
    }

    public String getRltId() {
        return rltId;
    }

    public void setRltId(String rltId) {
        this.rltId = rltId == null ? null : rltId.trim();
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

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }
}