package com.integration.generator.entity;

import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCiDir
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 目录实体类
*/
public class IomCiDir {
    private String id;

    private String dirName;

    private Integer dirType;

    private String parentDirId;

    private Integer dirLvl;

    private String dirPath;

    private Integer sort;

    private Integer isLeaf;

    private String dirIcon;

    private String dirColor;

    private String dirDesc;

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

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName == null ? null : dirName.trim();
    }

    public Integer getDirType() {
        return dirType;
    }

    public void setDirType(Integer dirType) {
        this.dirType = dirType;
    }

    public String getParentDirId() {
        return parentDirId;
    }

    public void setParentDirId(String parentDirId) {
        this.parentDirId = parentDirId == null ? null : parentDirId.trim();
    }

    public Integer getDirLvl() {
        return dirLvl;
    }

    public void setDirLvl(Integer dirLvl) {
        this.dirLvl = dirLvl;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath == null ? null : dirPath.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getDirIcon() {
        return dirIcon;
    }

    public void setDirIcon(String dirIcon) {
        this.dirIcon = dirIcon == null ? null : dirIcon.trim();
    }

    public String getDirColor() {
        return dirColor;
    }

    public void setDirColor(String dirColor) {
        this.dirColor = dirColor == null ? null : dirColor.trim();
    }

    public String getDirDesc() {
        return dirDesc;
    }

    public void setDirDesc(String dirDesc) {
        this.dirDesc = dirDesc == null ? null : dirDesc.trim();
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