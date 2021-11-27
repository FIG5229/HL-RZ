

package com.integration.entity;


/**
 * @author hbr
 * @version 1.0
 * @date 2018-12-03 09:49:27
 */
public class CiIconInfo extends BaseEntity {


    /**
     * ID
     */
    private String id;


    /**
     * 图标名称
     */
    private String icon_name;


    /**
     * 图标目录
     */
    private String icon_dir;


    /**
     * 图标类型1=CI分类图标    2=视图背景图
     */
    private Integer icon_type;


    /**
     * 图标描述
     */
    private String icon_desc;


    /**
     * 图标格式
     */
    private String icon_form;


    /**
     * 图标颜色
     */
    private String icon_color;


    /**
     * 图标路径
     */
    private String icon_path;


    /**
     * 图标大小
     */
    private Integer icon_size;


    /**
     * 权限范围
     */
    private Integer icon_range;


    /**
     * 排序列
     */
    private Integer sort;


    /**
     * 上传人
     */
    private String scr_id;


    /**
     * 上传时间
     */
    private String scsj;


    /**
     * 修改人
     */
    private String xgr_id;


    /**
     * 修改时间
     */
    private String xgsj;


    /**
     * 有效标志
     */
    private Integer yxbz;

    /**
     * 图标全限定名称:目录名称|图标名称  如：常用图标|Firewall
     */
    private String icon_full_name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getIcon_name() {
        return icon_name;
    }

    public void setIcon_name(String icon_name) {
        this.icon_name = icon_name;
    }

    public String getIcon_dir() {
        return icon_dir;
    }

    public void setIcon_dir(String icon_dir) {
        this.icon_dir = icon_dir;
    }

    public Integer getIcon_type() {
        return icon_type;
    }

    public void setIcon_type(Integer icon_type) {
        this.icon_type = icon_type;
    }


    public String getIcon_desc() {
        return icon_desc;
    }

    public void setIcon_desc(String icon_desc) {
        this.icon_desc = icon_desc;
    }


    public String getIcon_form() {
        return icon_form;
    }

    public void setIcon_form(String icon_form) {
        this.icon_form = icon_form;
    }


    public String getIcon_color() {
        return icon_color;
    }

    public void setIcon_color(String icon_color) {
        this.icon_color = icon_color;
    }


    public String getIcon_path() {
        return icon_path;
    }

    public void setIcon_path(String icon_path) {
        this.icon_path = icon_path;
    }


    public Integer getIcon_size() {
        return icon_size;
    }

    public void setIcon_size(Integer icon_size) {
        this.icon_size = icon_size;
    }


    public Integer getIcon_range() {
        return icon_range;
    }

    public void setIcon_range(Integer icon_range) {
        this.icon_range = icon_range;
    }


    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }


    public String getScr_id() {
        return scr_id;
    }

    public void setScr_id(String scr_id) {
        this.scr_id = scr_id;
    }


    public String getScsj() {
        return scsj;
    }

    public void setScsj(String scsj) {
        this.scsj = scsj;
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


    public Integer getYxbz() {
        return yxbz;
    }

    public void setYxbz(Integer yxbz) {
        this.yxbz = yxbz;
    }

    public String getIcon_full_name() {
        return icon_full_name;
    }

    public void setIcon_full_name(String icon_full_name) {
        this.icon_full_name = icon_full_name;
    }

    @Override
    public String toString() {
        return "CiIconInfo{" +
                "id='" + id + '\'' +
                ", icon_name='" + icon_name + '\'' +
                ", icon_dir=" + icon_dir +
                ", icon_type=" + icon_type +
                ", icon_desc='" + icon_desc + '\'' +
                ", icon_form='" + icon_form + '\'' +
                ", icon_color='" + icon_color + '\'' +
                ", icon_path='" + icon_path + '\'' +
                ", icon_size=" + icon_size +
                ", icon_range=" + icon_range +
                ", sort=" + sort +
                ", scr_id=" + scr_id +
                ", scsj='" + scsj + '\'' +
                ", xgr_id=" + xgr_id +
                ", xgsj='" + xgsj + '\'' +
                ", yxbz=" + yxbz +
                '}';
    }
}