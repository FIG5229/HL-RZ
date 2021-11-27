package com.integration.entity;

import java.util.List;
/**
* @Package: com.integration.entity
* @ClassName: Czry
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 操作人员
*/
public class Czry extends BaseEntity {
    private String id;
    private String czry_dm;
    private String czry_mc;
    /**
     * 部门名称
     */
    private String zzjg_mc;
    /**
     * 机构（单位）名称
     */
    private String org_mc;
    private String mobile_no;
    private String email_addres;
    private String czry_dldm;
    private String czry_pass;
    private String czry_short;
    private String allow_pass;
    private String last_login_time;
    private Integer login_bz;
    private Integer super_bz;
    private Integer lock_bz;
    private Integer pass_days;
    private Integer up_pass_bz;
    private Integer status;
    private Integer sort;
    private String cjr_id;
    private String cjsj;
    private String xgr_id;
    private String xgsj;
    private String yxbz;
    private List<CzryRole> roleList;
    /**
     * 部门ID
     */
    private String dept_id;
    /**
     * 机构（单位）ID
     */
    private String org_id;
    /**
     * 默认数据域ID
     */
    private String domainId;
    /**
     * 查询数据所用数据域
     */
    private String dataDomain;
    /**
     * 所在机构数据域（用于保存数据）
     */
    private String orgDomain;

    public List<CzryRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<CzryRole> roleList) {
        this.roleList = roleList;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCzry_dm() {
        return czry_dm;
    }

    public void setCzry_dm(String czry_dm) {
        this.czry_dm = czry_dm;
    }

    public String getCzry_mc() {
        return czry_mc;
    }

    public void setCzry_mc(String czry_mc) {
        this.czry_mc = czry_mc;
    }

    public String getZzjg_mc() {
        return zzjg_mc;
    }

    public void setZzjg_mc(String zzjg_mc) {
        this.zzjg_mc = zzjg_mc;
    }

    public String getOrg_mc() {
        return org_mc;
    }

    public void setOrg_mc(String org_mc) {
        this.org_mc = org_mc;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmail_addres() {
        return email_addres;
    }

    public void setEmail_addres(String email_addres) {
        this.email_addres = email_addres;
    }

    public String getCzry_dldm() {
        return czry_dldm;
    }

    public void setCzry_dldm(String czry_dldm) {
        this.czry_dldm = czry_dldm;
    }

    public String getCzry_pass() {
        return czry_pass;
    }

    public void setCzry_pass(String czry_pass) {
        this.czry_pass = czry_pass;
    }

    public String getCzry_short() {
        return czry_short;
    }

    public void setCzry_short(String czry_short) {
        this.czry_short = czry_short;
    }

    public String getAllow_pass() {
        return allow_pass;
    }

    public void setAllow_pass(String allow_pass) {
        this.allow_pass = allow_pass;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public Integer getLogin_bz() {
        return login_bz;
    }

    public void setLogin_bz(Integer login_bz) {
        this.login_bz = login_bz;
    }

    public Integer getSuper_bz() {
        return super_bz;
    }

    public void setSuper_bz(Integer super_bz) {
        this.super_bz = super_bz;
    }

    public Integer getLock_bz() {
        return lock_bz;
    }

    public void setLock_bz(Integer lock_bz) {
        this.lock_bz = lock_bz;
    }

    public Integer getPass_days() {
        return pass_days;
    }

    public void setPass_days(Integer pass_days) {
        this.pass_days = pass_days;
    }

    public Integer getUp_pass_bz() {
        return up_pass_bz;
    }

    public void setUp_pass_bz(Integer up_pass_bz) {
        this.up_pass_bz = up_pass_bz;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public String getCjr_id() {
        return cjr_id;
    }

    public void setCjr_id(String cjr_id) {
        this.cjr_id = cjr_id;
    }

    public String getXgr_id() {
        return xgr_id;
    }

    public void setXgr_id(String xgr_id) {
        this.xgr_id = xgr_id;
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

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getDataDomain() {
        return dataDomain;
    }

    public void setDataDomain(String dataDomain) {
        this.dataDomain = dataDomain;
    }

    public String getOrgDomain() {
        return orgDomain;
    }

    public void setOrgDomain(String orgDomain) {
        this.orgDomain = orgDomain;
    }
}
