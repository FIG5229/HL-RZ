package com.integration.generator.entity;

import java.util.Date;

import com.integration.entity.vo.IomCiRltNodeVO;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiRltNode
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class IomCiRltNode extends IomCiRltNodeVO{
    private String id;

    private String ruleId;

    private Integer rltType;

    private Integer nodeType;

    private Integer nodeLogic;

    private String typeId;

    private String nodeTypeId;

    private String pageNodeId;

    private Integer x;

    private Integer y;

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

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public Integer getNodeLogic() {
        return nodeLogic;
    }

    public void setNodeLogic(Integer nodeLogic) {
        this.nodeLogic = nodeLogic;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getNodeTypeId() {
        return nodeTypeId;
    }

    public void setNodeTypeId(String nodeTypeId) {
        this.nodeTypeId = nodeTypeId == null ? null : nodeTypeId.trim();
    }

    public String getPageNodeId() {
        return pageNodeId;
    }

    public void setPageNodeId(String pageNodeId) {
        this.pageNodeId = pageNodeId == null ? null : pageNodeId.trim();
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
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