package com.integration.generator.entity;

import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiIcon
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 图标实体类
*/
public class IomCiIcon {
    private String id;

    private String iconName;

    private String iconDir;

    private Integer iconType;

    private String iconDesc;

    private String iconForm;

    private String iconColor;

    private String iconPath;

    private Integer iconSize;

    private Integer iconRange;

    private Integer sort;

    private Long scrId;

    private Date scsj;

    private Long xgrId;

    private Date xgsj;

    private Integer yxbz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName == null ? null : iconName.trim();
    }

    public String getIconDir() {
        return iconDir;
    }

    public void setIconDir(String iconDir) {
        this.iconDir = iconDir == null ? null : iconDir.trim();
    }

    public Integer getIconType() {
        return iconType;
    }

    public void setIconType(Integer iconType) {
        this.iconType = iconType;
    }

    public String getIconDesc() {
        return iconDesc;
    }

    public void setIconDesc(String iconDesc) {
        this.iconDesc = iconDesc == null ? null : iconDesc.trim();
    }

    public String getIconForm() {
        return iconForm;
    }

    public void setIconForm(String iconForm) {
        this.iconForm = iconForm == null ? null : iconForm.trim();
    }

    public String getIconColor() {
        return iconColor;
    }

    public void setIconColor(String iconColor) {
        this.iconColor = iconColor == null ? null : iconColor.trim();
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath == null ? null : iconPath.trim();
    }

    public Integer getIconSize() {
        return iconSize;
    }

    public void setIconSize(Integer iconSize) {
        this.iconSize = iconSize;
    }

    public Integer getIconRange() {
        return iconRange;
    }

    public void setIconRange(Integer iconRange) {
        this.iconRange = iconRange;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getScrId() {
        return scrId;
    }

    public void setScrId(Long scrId) {
        this.scrId = scrId;
    }

    public Date getScsj() {
        return scsj;
    }

    public void setScsj(Date scsj) {
        this.scsj = scsj;
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