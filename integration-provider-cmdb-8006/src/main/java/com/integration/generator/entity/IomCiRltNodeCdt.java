package com.integration.generator.entity;

import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiRltNodeCdt
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class IomCiRltNodeCdt {
    private String id;

    private String nodeId;

    private String attrId;

    private String cdtOp;

    private String cdtVal;

    private Integer isNot;

    private Integer stor;

    private Integer domainId;

    private Date cjsj;

    private Date xgsj;

    private Integer yxbz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId == null ? null : attrId.trim();
    }

    public String getCdtOp() {
        return cdtOp;
    }

    public void setCdtOp(String cdtOp) {
        this.cdtOp = cdtOp == null ? null : cdtOp.trim();
    }

    public String getCdtVal() {
        return cdtVal;
    }

    public void setCdtVal(String cdtVal) {
        this.cdtVal = cdtVal == null ? null : cdtVal.trim();
    }

    public Integer getIsNot() {
        return isNot;
    }

    public void setIsNot(Integer isNot) {
        this.isNot = isNot;
    }

    public Integer getStor() {
        return stor;
    }

    public void setStor(Integer stor) {
        this.stor = stor;
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