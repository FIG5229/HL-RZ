package com.integration.entity;

import java.util.List;

/**
 * @author hbr
 * @date 2018-12-17 14:49:01
 * @version 1.0
 */
public class Tpl_Item {
    /**
     * 主键
     */
    private String id;


    /**
     * 模板名称
     */
    private String tpl_name;


    /**
     * 模板别名
     */
    private String tpl_alias;


    /**
     * 模板描述
     */
    private String tpl_desc;

    /**
     * 指标组
     */
    private List<CiKpiTpIItemInfo> kpiList;

    /**
     * 对象组
     */
    private List<CiKpiTpIItemInfo> objList;

    public List<CiKpiTpIItemInfo> getKpiList() {
        return kpiList;
    }

    public void setKpiList(List<CiKpiTpIItemInfo> kpiList) {
        this.kpiList = kpiList;
    }

    public List<CiKpiTpIItemInfo> getObjList() {
        return objList;
    }

    public void setObjList(List<CiKpiTpIItemInfo> objList) {
        this.objList = objList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTpl_name() {
        return tpl_name;
    }

    public void setTpl_name(String tpl_name) {
        this.tpl_name = tpl_name;
    }

    public String getTpl_alias() {
        return tpl_alias;
    }

    public void setTpl_alias(String tpl_alias) {
        this.tpl_alias = tpl_alias;
    }

    public String getTpl_desc() {
        return tpl_desc;
    }

    public void setTpl_desc(String tpl_desc) {
        this.tpl_desc = tpl_desc;
    }
}