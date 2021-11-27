package com.integration.entity;

/**
 * @ProjectName: integration
 * @Package: com.integration.entity
 * @ClassName: CiSourceRel
 * @Author: ztl
 * @Date: 2021-08-16
 * @Version: 1.0
 * @description:数据源和对象关联关系
 */
public class CiSourceRel {
    /**
     * 主键
     */
    private String id;
    /**
     * 数据来源ID
     */
    private String sourceId;
    /**
     * 数据来源名称
     */
    private String sourceName;
    /**
     * 大类ID
     */
    private String ciTypeId;
    /**
     * 大类名称
     */
    private String ciTypeName;
    /**
     * CICODE
     */
    private String ciCode;
    /**
     * CI名称
     */
    private String ciName;
    /**
     * 数据域
     */
    private String domainId;
    /**
     * 创建人ID
     */
    private String cjrId;
    /**
     * 创建人名称
     */
    private String cjrName;
    /**
     * 创建时间
     */
    private String cjsj;
    /**
     * 修改人ID
     */
    private String xgrId;
    /**
     * 修改人名称
     */
    private String xgrName;
    /**
     * 修改时间
     */
    private String xgsj;
    /**
     * 有效标志
     */
    private String yxbz;
    /**
     * CICODE逗号分隔字符串
     */
    private String ciCodes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getCiTypeId() {
        return ciTypeId;
    }

    public void setCiTypeId(String ciTypeId) {
        this.ciTypeId = ciTypeId;
    }

    public String getCiTypeName() {
        return ciTypeName;
    }

    public void setCiTypeName(String ciTypeName) {
        this.ciTypeName = ciTypeName;
    }

    public String getCiCode() {
        return ciCode;
    }

    public void setCiCode(String ciCode) {
        this.ciCode = ciCode;
    }

    public String getCiName() {
        return ciName;
    }

    public void setCiName(String ciName) {
        this.ciName = ciName;
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

    public String getCjrName() {
        return cjrName;
    }

    public void setCjrName(String cjrName) {
        this.cjrName = cjrName;
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

    public String getXgrName() {
        return xgrName;
    }

    public void setXgrName(String xgrName) {
        this.xgrName = xgrName;
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

    public String getCiCodes() {
        return ciCodes;
    }

    public void setCiCodes(String ciCodes) {
        this.ciCodes = ciCodes;
    }
}
