package com.integration.entity;

/**
 * @ProjectName: integration
 * @Package: com.integration.entity
 * @ClassName: CiLabel
 * @Author: ztl
 * @Date: 2020-12-04
 * @Version: 1.0
 * @description:CI标签
 */
public class CiLabel {

    /**
     * 主键
     */
    private String id;
    /**
     * 标签名
     */
    private String labelName;
    /**
     * 目录ID
     */
    private String dirId;
    /**
     * 标签规则
     */
    private String labelRuleInfo;
    /**
     * 数据域
     */
    private String domainId;
    /**
     * 创建人
     */
    private String cjrId;
    /**
     * 创建时间
     */
    private String cjsj;
    /**
     * 修改人
     */
    private String xgrId;
    /**
     * 修改时间
     */
    private String xgsj;

    /**
     * 有效标志
     */
    private String yxbz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getDirId() {
        return dirId;
    }

    public void setDirId(String dirId) {
        this.dirId = dirId;
    }

    public String getLabelRuleInfo() {
        return labelRuleInfo;
    }

    public void setLabelRuleInfo(String labelRuleInfo) {
        this.labelRuleInfo = labelRuleInfo;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getCjrId() {
        return cjrId;
    }

    public void setCjrId(String cjrId) {
        this.cjrId = cjrId;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public String getXgrId() {
        return xgrId;
    }

    public void setXgrId(String xgrId) {
        this.xgrId = xgrId;
    }

    public String getXgsj() {
        return xgsj;
    }

    public void setXgsj(String xgsj) {
        this.xgsj = xgsj;
    }

    public String getYxbz() {
        return yxbz;
    }

    public void setYxbz(String yxbz) {
        this.yxbz = yxbz;
    }
}
