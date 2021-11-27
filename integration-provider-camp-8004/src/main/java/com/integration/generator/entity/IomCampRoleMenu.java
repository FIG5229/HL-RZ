package com.integration.generator.entity;

import java.util.Date;
/**
* @Package: com.integration.generator.entity
* @ClassName: IomCampRoleMenu
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 角色菜单关系实体类
*/
public class IomCampRoleMenu {
    private String id;

    private String roleDm;

    private String gncdDm;

    private Integer gnflType;

    private String cjrId;

    private Date cjsj;

    private String xgrId;

    private Date xgsj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRoleDm() {
        return roleDm;
    }

    public void setRoleDm(String roleDm) {
        this.roleDm = roleDm == null ? null : roleDm.trim();
    }

    public String getGncdDm() {
        return gncdDm;
    }

    public void setGncdDm(String gncdDm) {
        this.gncdDm = gncdDm == null ? null : gncdDm.trim();
    }

    public Integer getGnflType() {
        return gnflType;
    }

    public void setGnflType(Integer gnflType) {
        this.gnflType = gnflType;
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
}