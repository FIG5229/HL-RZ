package com.integration.entity;

/**
 * @ProjectName: integration
 * @Package: com.integration.entity
 * @ClassName: InfoHump
 * @Author: ztl
 * @Date: 2020-07-14
 * @Version: 1.0
 * @description:CI实体类（驼峰）
 */
public class InfoHump {

    /**
     * id
     */
    private String id;

    /**
     * ci编码
     */
    private String ciBm;

    private String ciId;
    /**
     * ci描述
     */
    private String ciDesc;

    /**
     * 所属类别
     */
    private String ciTypeId;

    /**
     * 所属类别编码
     */
    private String ciTypeBm;

    /**
     * 来源
     */
    private int sourceId;

    /**
     * 所有者
     */
    private int ownerId;

    /**
     * 所属组织
     */
    private int orgId;

    /**
     * CI版本
     */
    private String ciVersion;

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

    /**
     * 数据域ID
     */
    private String domainId;

    private String ciTypeIcon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCiBm() {
        return ciBm;
    }

    public void setCiBm(String ciBm) {
        this.ciBm = ciBm;
    }

    public String getCiDesc() {
        return ciDesc;
    }

    public void setCiDesc(String ciDesc) {
        this.ciDesc = ciDesc;
    }

    public String getCiTypeId() {
        return ciTypeId;
    }

    public void setCiTypeId(String ciTypeId) {
        this.ciTypeId = ciTypeId;
    }

    public String getCiTypeBm() {
        return ciTypeBm;
    }

    public void setCiTypeBm(String ciTypeBm) {
        this.ciTypeBm = ciTypeBm;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getCiVersion() {
        return ciVersion;
    }

    public void setCiVersion(String ciVersion) {
        this.ciVersion = ciVersion;
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

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getCiTypeIcon() {
        return ciTypeIcon;
    }

    public void setCiTypeIcon(String ciTypeIcon) {
        this.ciTypeIcon = ciTypeIcon;
    }

    public String getCiId() {
        return ciId;
    }

    public void setCiId(String ciId) {
        this.ciId = ciId;
    }
}
