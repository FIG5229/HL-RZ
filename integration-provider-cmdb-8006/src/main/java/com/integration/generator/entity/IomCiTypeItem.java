package com.integration.generator.entity;

import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiTypeItem
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 类定义实体类
*/
public class IomCiTypeItem {
    private String id;

    private String ciTypeId;

    private String attrName;

    private String attrStdName;

    private String attrType;

    private String attrDesc;

    private String mpCiItem;

    private Integer isMajor;

    private Integer isRequ;

    private String defVal;

    private String enumVals;

    private Integer sort;

    private String cjrId;

    private Date cjsj;

    private String xgrId;

    private Date xgsj;

    private Integer yxbz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCiTypeId() {
        return ciTypeId;
    }

    public void setCiTypeId(String ciTypeId) {
        this.ciTypeId = ciTypeId == null ? null : ciTypeId.trim();
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName == null ? null : attrName.trim();
    }

    public String getAttrStdName() {
        return attrStdName;
    }

    public void setAttrStdName(String attrStdName) {
        this.attrStdName = attrStdName == null ? null : attrStdName.trim();
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType == null ? null : attrType.trim();
    }

    public String getAttrDesc() {
        return attrDesc;
    }

    public void setAttrDesc(String attrDesc) {
        this.attrDesc = attrDesc == null ? null : attrDesc.trim();
    }

    public String getMpCiItem() {
        return mpCiItem;
    }

    public void setMpCiItem(String mpCiItem) {
        this.mpCiItem = mpCiItem == null ? null : mpCiItem.trim();
    }

    public Integer getIsMajor() {
        return isMajor;
    }

    public void setIsMajor(Integer isMajor) {
        this.isMajor = isMajor;
    }

    public Integer getIsRequ() {
        return isRequ;
    }

    public void setIsRequ(Integer isRequ) {
        this.isRequ = isRequ;
    }

    public String getDefVal() {
        return defVal;
    }

    public void setDefVal(String defVal) {
        this.defVal = defVal == null ? null : defVal.trim();
    }

    public String getEnumVals() {
        return enumVals;
    }

    public void setEnumVals(String enumVals) {
        this.enumVals = enumVals == null ? null : enumVals.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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