package com.integration.generator.entity;

import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiType
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 大类管理
*/
public class IomCiType {
    private String id;

    private String ciTypeBm;

    private String ciTypeMc;

    private String ciTypeStdBm;

    private String ciTypeDir;

    private String parentCiTypeId;

    private Integer ciTypeLv;

    private String ciTypePath;

    private String leaf;

    private String ciTypeIcon;

    private String ciTypeDesc;

    private String ciTypeColor;

    private Integer sort;

    private Long cjrId;

    private Date cjsj;

    private Long xgrId;

    private Date xgsj;

    private Integer yxbz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCiTypeBm() {
        return ciTypeBm;
    }

    public void setCiTypeBm(String ciTypeBm) {
        this.ciTypeBm = ciTypeBm == null ? null : ciTypeBm.trim();
    }

    public String getCiTypeMc() {
        return ciTypeMc;
    }

    public void setCiTypeMc(String ciTypeMc) {
        this.ciTypeMc = ciTypeMc == null ? null : ciTypeMc.trim();
    }

    public String getCiTypeStdBm() {
        return ciTypeStdBm;
    }

    public void setCiTypeStdBm(String ciTypeStdBm) {
        this.ciTypeStdBm = ciTypeStdBm == null ? null : ciTypeStdBm.trim();
    }

    public String getCiTypeDir() {
        return ciTypeDir;
    }

    public void setCiTypeDir(String ciTypeDir) {
        this.ciTypeDir = ciTypeDir == null ? null : ciTypeDir.trim();
    }

    public String getParentCiTypeId() {
        return parentCiTypeId;
    }

    public void setParentCiTypeId(String parentCiTypeId) {
        this.parentCiTypeId = parentCiTypeId == null ? null : parentCiTypeId.trim();
    }

    public Integer getCiTypeLv() {
        return ciTypeLv;
    }

    public void setCiTypeLv(Integer ciTypeLv) {
        this.ciTypeLv = ciTypeLv;
    }

    public String getCiTypePath() {
        return ciTypePath;
    }

    public void setCiTypePath(String ciTypePath) {
        this.ciTypePath = ciTypePath == null ? null : ciTypePath.trim();
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf == null ? null : leaf.trim();
    }

    public String getCiTypeIcon() {
        return ciTypeIcon;
    }

    public void setCiTypeIcon(String ciTypeIcon) {
        this.ciTypeIcon = ciTypeIcon == null ? null : ciTypeIcon.trim();
    }

    public String getCiTypeDesc() {
        return ciTypeDesc;
    }

    public void setCiTypeDesc(String ciTypeDesc) {
        this.ciTypeDesc = ciTypeDesc == null ? null : ciTypeDesc.trim();
    }

    public String getCiTypeColor() {
        return ciTypeColor;
    }

    public void setCiTypeColor(String ciTypeColor) {
        this.ciTypeColor = ciTypeColor == null ? null : ciTypeColor.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getCjrId() {
        return cjrId;
    }

    public void setCjrId(Long cjrId) {
        this.cjrId = cjrId;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public Long getXgrId() {
        return xgrId;
    }

    public void setXgrId(Long xgrId) {
        this.xgrId = xgrId;
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