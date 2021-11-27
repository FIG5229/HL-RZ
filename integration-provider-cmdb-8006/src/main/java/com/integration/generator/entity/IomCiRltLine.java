package com.integration.generator.entity;

import java.io.Serializable;
import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiRltLine
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class IomCiRltLine implements Serializable {
    private String id;

    private String ruleId;

    private Integer rltType;

    private String typeId;

    private String startTypeId;

    private String endTypeId;

    private String rltId;

    private String startNodeId;

    private String endNodeId;

    private Integer lineType;

    private String lineOp;

    private String lineVal;

    private Integer opType;

    private Integer domainId;

    private Date cjsj;

    private Date xgsj;

    private Integer yxbz;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId == null ? null : ruleId.trim();
    }

    public Integer getRltType() {
        return rltType;
    }

    public void setRltType(Integer rltType) {
        this.rltType = rltType;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getStartTypeId() {
        return startTypeId;
    }

    public void setStartTypeId(String startTypeId) {
        this.startTypeId = startTypeId == null ? null : startTypeId.trim();
    }

    public String getEndTypeId() {
        return endTypeId;
    }

    public void setEndTypeId(String endTypeId) {
        this.endTypeId = endTypeId == null ? null : endTypeId.trim();
    }

    public String getRltId() {
        return rltId;
    }

    public void setRltId(String rltId) {
        this.rltId = rltId == null ? null : rltId.trim();
    }

    public String getStartNodeId() {
        return startNodeId;
    }

    public void setStartNodeId(String startNodeId) {
        this.startNodeId = startNodeId == null ? null : startNodeId.trim();
    }

    public String getEndNodeId() {
        return endNodeId;
    }

    public void setEndNodeId(String endNodeId) {
        this.endNodeId = endNodeId == null ? null : endNodeId.trim();
    }

    public Integer getLineType() {
        return lineType;
    }

    public void setLineType(Integer lineType) {
        this.lineType = lineType;
    }

    public String getLineOp() {
        return lineOp;
    }

    public void setLineOp(String lineOp) {
        this.lineOp = lineOp == null ? null : lineOp.trim();
    }

    public String getLineVal() {
        return lineVal;
    }

    public void setLineVal(String lineVal) {
        this.lineVal = lineVal == null ? null : lineVal.trim();
    }

    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
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