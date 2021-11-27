package com.integration.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class SgccUser {
    private String userId;

    private String account;

    private String name;

    private String nameCode;

    private String provinceId;

    private String saphrId;

    private String baseOrgId;

    private String baseOrgName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    private String baseOrgIdPath;

    private String unitName;

    private String unitId;

    private String baseOrgNamePath;

    private String state;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startUsefulLife;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endUsefulLife;

    private String userType;

    private String showContact;

    private String useContact;

    private String postLevel;

    private String deptId;

    private String postBaseorgId;

    private BigDecimal cjrId;

    private Date cjsj;

    private BigDecimal xgrId;

    private Date xgsj;

    private Integer yxbz;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNameCode() {
        return nameCode;
    }

    public void setNameCode(String nameCode) {
        this.nameCode = nameCode == null ? null : nameCode.trim();
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId == null ? null : provinceId.trim();
    }

    public String getSaphrId() {
        return saphrId;
    }

    public void setSaphrId(String saphrId) {
        this.saphrId = saphrId == null ? null : saphrId.trim();
    }

    public String getBaseOrgId() {
        return baseOrgId;
    }

    public void setBaseOrgId(String baseOrgId) {
        this.baseOrgId = baseOrgId == null ? null : baseOrgId.trim();
    }

    public String getBaseOrgName() {
        return baseOrgName;
    }

    public void setBaseOrgName(String baseOrgName) {
        this.baseOrgName = baseOrgName == null ? null : baseOrgName.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getBaseOrgIdPath() {
        return baseOrgIdPath;
    }

    public void setBaseOrgIdPath(String baseOrgIdPath) {
        this.baseOrgIdPath = baseOrgIdPath == null ? null : baseOrgIdPath.trim();
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    public String getBaseOrgNamePath() {
        return baseOrgNamePath;
    }

    public void setBaseOrgNamePath(String baseOrgNamePath) {
        this.baseOrgNamePath = baseOrgNamePath == null ? null : baseOrgNamePath.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getStartUsefulLife() {
        return startUsefulLife;
    }

    public void setStartUsefulLife(Date startUsefulLife) {
        this.startUsefulLife = startUsefulLife;
    }

    public Date getEndUsefulLife() {
        return endUsefulLife;
    }

    public void setEndUsefulLife(Date endUsefulLife) {
        this.endUsefulLife = endUsefulLife;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getShowContact() {
        return showContact;
    }

    public void setShowContact(String showContact) {
        this.showContact = showContact == null ? null : showContact.trim();
    }

    public String getUseContact() {
        return useContact;
    }

    public void setUseContact(String useContact) {
        this.useContact = useContact == null ? null : useContact.trim();
    }

    public String getPostLevel() {
        return postLevel;
    }

    public void setPostLevel(String postLevel) {
        this.postLevel = postLevel == null ? null : postLevel.trim();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getPostBaseorgId() {
        return postBaseorgId;
    }

    public void setPostBaseorgId(String postBaseorgId) {
        this.postBaseorgId = postBaseorgId == null ? null : postBaseorgId.trim();
    }

    public BigDecimal getCjrId() {
        return cjrId;
    }

    public void setCjrId(BigDecimal cjrId) {
        this.cjrId = cjrId;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public BigDecimal getXgrId() {
        return xgrId;
    }

    public void setXgrId(BigDecimal xgrId) {
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