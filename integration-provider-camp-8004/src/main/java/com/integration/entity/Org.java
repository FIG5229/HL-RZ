package com.integration.entity;

import java.util.List;

/**
 * 组织机构
 *
 * @author zhuxd
 * @date 2019-07-31
 */
public class Org extends BaseEntity {
    /**
     * 机构ID
     */
    private String id;
    /**
     * 机构名称
     */
    private String name;
    /**
     * 上级机构ID
     */
    private String pid;
    /**
     * 部门标志
     */
    private String is_dept;
    /**
     * 机构类别 部门时代表部门类别
     * 部门标志为是时代表部门类别
     * 部门标志为否时代表机构类别  01 代表省 02 代表市 03代表区或县 04 代表所 逐层递增
     */
    private String service_type;
    /**
     * 路径
     */
    private String path;
    /**
     * 机构状态
     */
    private String status;
    /**
     * 机构排序
     */
    private String sort;
    /**
     * 创建人
     */
    private String cjr_id;
    /**
     * 创建时间
     */
    private String cjsj;
    /**
     * 修改人
     */
    private String xgr_id;
    /**
     * 修改时间
     */
    private String xgsj;
    /**
     * 有效标志 1有效 0删除
     */
    private String yxbz;

    /**
     * 子节点数量（一级子节点）
     */
    private String child_count;
    /**
     * 子节点列表
     */
    private List<Org> children;
    /**
     * 数据域
     */
    private String dataDomain;
    /**
     * 操作人员列表
     */
    private List<Czry> czryList;

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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getse() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getIs_dept() {
        return is_dept;
    }

    public void setIs_dept(String is_dept) {
        this.is_dept = is_dept;
    }

    public String getPath() { return path; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCjr_id() {
        return cjr_id;
    }

    public void setCjr_id(String cjr_id) {
        this.cjr_id = cjr_id;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
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

    public String getChild_count() {
        return child_count;
    }

    public void setChild_count(String child_count) {
        this.child_count = child_count;
    }

    public List<Org> getChildren() {
        return children;
    }

    public void setChildren(List<Org> children) {
        this.children = children;
    }

    public String getDataDomain() {
        return dataDomain;
    }

    public void setDataDomain(String dataDomain) {
        this.dataDomain = dataDomain;
    }

    public List<Czry> getCzryList() {
        return czryList;
    }

    public void setCzryList(List<Czry> czryList) {
        this.czryList = czryList;
    }
}
