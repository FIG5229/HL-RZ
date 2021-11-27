package com.integration.entity;

import java.util.List;
/**
* @Package: com.integration.entity
* @ClassName: TplItem
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class TplItem {

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
     * 有效标志
     */
    private Integer yxbz;

    /**
     * 关联对象ID
     */
    private List<String> obj_id;

    /**
     * 关联对象类型1:指标;2:CI类别;3:标签
     */
    private List<Integer> obj_type;

    /**
     * 关联对象名称
     */
    private List<String> obj_name;

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

    public Integer getYxbz() {
        return yxbz;
    }

    public void setYxbz(Integer yxbz) {
        this.yxbz = yxbz;
    }

    public List<String> getObj_id() {
        return obj_id;
    }

    public void setObj_id(List<String> obj_id) {
        this.obj_id = obj_id;
    }

    public List<Integer> getObj_type() {
        return obj_type;
    }

    public void setObj_type(List<Integer> obj_type) {
        this.obj_type = obj_type;
    }

    public List<String> getObj_name() {
        return obj_name;
    }

    public void setObj_name(List<String> obj_name) {
        this.obj_name = obj_name;
    }

    @Override
    public String toString() {
        return "TplItem{" +
                "tpl_name='" + tpl_name + '\'' +
                ", tpl_alias='" + tpl_alias + '\'' +
                ", tpl_desc='" + tpl_desc + '\'' +
                ", yxbz=" + yxbz +
                ", obj_id=" + obj_id +
                ", obj_type=" + obj_type +
                ", obj_name=" + obj_name +
                '}';
    }
}