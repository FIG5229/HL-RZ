package com.integration.entity;

/**
 * @ProjectName: integration
 * @Package: com.integration.entity
 * @ClassName: CiAssociatedFieldConf
 * @Author: ztl
 * @Date: 2021-07-07
 * @Version: 1.0
 * @description:关联字段配置表
 */

public class CiAssociatedFieldConf {

    /**
     * 主键
     */
    private String id;

    /**
     * 数据源ID
     */
    private String sourceCiId;

    /**
     * 数据源名称
     */
    private String sourceCiName;

    /**
     * CI大类ID
     */
    private String ciTypeId;

    /**
     * CI大类名称
     */
    private String ciTypeName;

    /**
     * CI属性ID
     */
    private String ciItemId;

    /**
     * CI属性名称
     */
    private String ciItemName;

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
    private Integer yxbz;

    /**
     * 是否触发
     */
    private boolean isTrigger;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceCiId() {
        return sourceCiId;
    }

    public void setSourceCiId(String sourceCiId) {
        this.sourceCiId = sourceCiId;
    }

    public String getSourceCiName() {
        return sourceCiName;
    }

    public void setSourceCiName(String sourceCiName) {
        this.sourceCiName = sourceCiName;
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

    public String getCiItemId() {
        return ciItemId;
    }

    public void setCiItemId(String ciItemId) {
        this.ciItemId = ciItemId;
    }

    public String getCiItemName() {
        return ciItemName;
    }

    public void setCiItemName(String ciItemName) {
        this.ciItemName = ciItemName;
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

    public Integer getYxbz() {
        return yxbz;
    }

    public void setYxbz(Integer yxbz) {
        this.yxbz = yxbz;
    }

    public String getXgrName() {
        return xgrName;
    }

    public void setXgrName(String xgrName) {
        this.xgrName = xgrName;
    }

    public boolean isTrigger() {
        return isTrigger;
    }

    public void setIsTrigger(boolean isTrigger) {
        this.isTrigger = isTrigger;
    }
}
