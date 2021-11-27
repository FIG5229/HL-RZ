package com.integration.generator.entity;

import java.io.Serializable;
import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiRltRule
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class IomCiRltRule implements Serializable {
    private String id;

    private Integer rltType;

    private String rltName;

    private String typeId;

    private String rltDesc;

    private String diagXml;

    private Integer status;

    private String validDesc;

    private Integer domainId;

    private String cjrId;

    private Date cjsj;

    private String xgrId;

    private Date xgsj;

    private Integer yxbz;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getRltType() {
        return rltType;
    }

    public void setRltType(Integer rltType) {
        this.rltType = rltType;
    }

    public String getRltName() {
        return rltName;
    }

    public void setRltName(String rltName) {
        this.rltName = rltName == null ? null : rltName.trim();
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getRltDesc() {
        return rltDesc;
    }

    public void setRltDesc(String rltDesc) {
        this.rltDesc = rltDesc == null ? null : rltDesc.trim();
    }

    public String getDiagXml() {
        return diagXml;
    }

    public void setDiagXml(String diagXml) {
        this.diagXml = diagXml == null ? null : diagXml.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getValidDesc() {
        return validDesc;
    }

    public void setValidDesc(String validDesc) {
        this.validDesc = validDesc == null ? null : validDesc.trim();
    }

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
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