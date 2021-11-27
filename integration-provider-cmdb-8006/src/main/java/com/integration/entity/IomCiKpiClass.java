package com.integration.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 指标大类表(IomCiKpiClass)实体类
 *
 * @author makejava
 * @since 2019-11-01 11:37:02
 */
public class IomCiKpiClass implements Serializable {
    private static final long serialVersionUID = -39065391550445256L;
    /**
     * 主键
     */
    private String id;
    /**
     * 指标类名称
     */
    private String name;
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
    private Date xgsj;
    /**
     * 有效标志
     */
    private Integer yxbz;
    /**
     * 数据域ID
     */
    private String domain_id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(String domain_id) {
        this.domain_id = domain_id;
    }
}