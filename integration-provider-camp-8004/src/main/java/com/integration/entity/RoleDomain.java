package com.integration.entity;

/**
 * @ProjectName: integration
 * @Package: com.integration.entity
 * @ClassName: RoleDomain
 * @Author: ztl
 * @Date: 2021-07-19
 * @Version: 1.0
 * @description:角色和数据域关联表
 */
public class RoleDomain {

    /**
     * 主键
     */
    private String id;

    /**
     * 角色代码
     */
    private String roleDm;

    /**
     * 角色对应数据域，多个用逗号分隔
     */
    private String dataDomain;

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

    public String getDataDomain() {
        return dataDomain;
    }

    public void setDataDomain(String dataDomain) {
        this.dataDomain = dataDomain;
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
}
