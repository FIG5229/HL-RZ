package com.integration.entity;

/**
 * @ProjectName: integration
 * @Package: com.integration.entity
 * @ClassName: CiAssociatedFieldConf
 * @Author: ztl
 * @Date: 2021-07-09
 * @Version: 1.0
 * @description:关联字段配置表
 */

public class CiAssociatedTrigger {

    /**
     * 主键
     */
    private String id;

    /**
     * 配置主键
     */
    private String confId;

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
     * 有效标志
     */
    private Integer yxbz;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConfId() {
        return confId;
    }

    public void setConfId(String confId) {
        this.confId = confId;
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

    public Integer getYxbz() {
        return yxbz;
    }

    public void setYxbz(Integer yxbz) {
        this.yxbz = yxbz;
    }



}
