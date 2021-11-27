package com.integration.entity;

/**
 * @ProjectName: integration
 * @Package: com.integration.entity
 * @ClassName: RoleData
 * @Author: ztl
 * @Date: 2020-06-10
 * @Version: 1.0
 * @description:角色代码与数据权限（CI大类）关联表
 */
public class RoleData {

    /**
     * ID
     */
    private String id;

    /**
     * 角色代码
     */
    private String roleDm;

    /**
     * 数据ID
     */
    private String dataId;

    /**
     * 顺序 查询 、编辑、删除、新增  1代表有权限 0代表无权限， 后期扩展时往后累加
     */
    private Integer authValue;

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
     * ci大类名称
     */
    private String ciTypeMc;

    /**
     * 查看权限
     */
    private Boolean searchAuth;

    /**
     * 编辑权限
     */
    private Boolean editAuth;

    /**
     * 删除权限
     */
    private Boolean deleteAuth;

    /**
     * 新增权限
     */
    private Boolean addAuth;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleDm() {
        return roleDm;
    }

    public void setRoleDm(String roleDm) {
        this.roleDm = roleDm;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public Integer getAuthValue() {
        return authValue;
    }

    public void setAuthValue(Integer authValue) {
        this.authValue = authValue;
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

    public String getCiTypeMc() {
        return ciTypeMc;
    }

    public void setCiTypeMc(String ciTypeMc) {
        this.ciTypeMc = ciTypeMc;
    }

    public Boolean getSearchAuth() {
        return searchAuth;
    }

    public void setSearchAuth(Boolean searchAuth) {
        this.searchAuth = searchAuth;
    }

    public Boolean getEditAuth() {
        return editAuth;
    }

    public void setEditAuth(Boolean editAuth) {
        this.editAuth = editAuth;
    }

    public Boolean getDeleteAuth() {
        return deleteAuth;
    }

    public void setDeleteAuth(Boolean deleteAuth) {
        this.deleteAuth = deleteAuth;
    }

    public Boolean getAddAuth() {
        return addAuth;
    }

    public void setAddAuth(Boolean addAuth) {
        this.addAuth = addAuth;
    }
}


