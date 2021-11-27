package com.integration.generator.entity;

import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiDataRel
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 关系管理实体类
*/
public class IomCiDataRel {
    private String id;

    private String relId;

    private String relName;

    private String relDesc;

    private Integer sourceId;

    private Integer owenrId;

    private Integer orgId;

    private String sourceCiId;

    private String sourceCiBm;

    private String sourceTypeBm;

    private String sourceTypeId;

    private String targetCiId;

    private String targetCiBm;

    private String targetTypeBm;

    private String targetTypeId;

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

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId == null ? null : relId.trim();
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName == null ? null : relName.trim();
    }

    public String getRelDesc() {
        return relDesc;
    }

    public void setRelDesc(String relDesc) {
        this.relDesc = relDesc == null ? null : relDesc.trim();
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getOwenrId() {
        return owenrId;
    }

    public void setOwenrId(Integer owenrId) {
        this.owenrId = owenrId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getSourceCiId() {
        return sourceCiId;
    }

    public void setSourceCiId(String sourceCiId) {
        this.sourceCiId = sourceCiId == null ? null : sourceCiId.trim();
    }

    public String getSourceCiBm() {
        return sourceCiBm;
    }

    public void setSourceCiBm(String sourceCiBm) {
        this.sourceCiBm = sourceCiBm == null ? null : sourceCiBm.trim();
    }

    public String getSourceTypeBm() {
        return sourceTypeBm;
    }

    public void setSourceTypeBm(String sourceTypeBm) {
        this.sourceTypeBm = sourceTypeBm == null ? null : sourceTypeBm.trim();
    }

    public String getSourceTypeId() {
        return sourceTypeId;
    }

    public void setSourceTypeId(String sourceTypeId) {
        this.sourceTypeId = sourceTypeId == null ? null : sourceTypeId.trim();
    }

    public String getTargetCiId() {
        return targetCiId;
    }

    public void setTargetCiId(String targetCiId) {
        this.targetCiId = targetCiId == null ? null : targetCiId.trim();
    }

    public String getTargetCiBm() {
        return targetCiBm;
    }

    public void setTargetCiBm(String targetCiBm) {
        this.targetCiBm = targetCiBm == null ? null : targetCiBm.trim();
    }

    public String getTargetTypeBm() {
        return targetTypeBm;
    }

    public void setTargetTypeBm(String targetTypeBm) {
        this.targetTypeBm = targetTypeBm == null ? null : targetTypeBm.trim();
    }

    public String getTargetTypeId() {
        return targetTypeId;
    }

    public void setTargetTypeId(String targetTypeId) {
        this.targetTypeId = targetTypeId == null ? null : targetTypeId.trim();
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