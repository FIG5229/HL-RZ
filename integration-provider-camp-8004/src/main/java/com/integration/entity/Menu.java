package com.integration.entity;

import java.util.List;
/**
* @Package: com.integration.entity
* @ClassName: Menu
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 菜单实体类
*/
public class Menu extends BaseEntity{
    /** 
     * 功能菜单代码
     */
    private String gncd_dm ;
    
    /** 
     * 功能菜单名称
     */
    private String gncd_mc ;
    
    /** 
     * 上级功能菜单代码
     */
    private String sj_gncd_dm ;
    
    /** 
     * 上级功能菜单名称
     */
    private String sj_gncd_mc ;
    
    /** 
     * 功能分类0枝干1叶子
     */
    private Integer gnfl ;
    
    /** 
     * 功能菜单层级
     */
    private Integer gncd_level ;
    
    /** 
     * 菜单类型1内置功能2系统管理3业务管理4业务功能
     */
    private Integer gncd_type ;
    
    /** 
     * 菜单图标路径
     */
    private String gncd_img ;
    
    /** 
     * 菜单地址
     */
    private String gncd_url ;
    
    /** 
     * 排序列
     */
    private Integer sort ;
    
    /** 
     * 创建人
     */
    private String cjr_id ;
    
    /** 
     * 创建时间
     */
    private String cjsj ;
    
    /** 
     * 修改人
     */
    private String xgr_id ;
    
    /** 
     * 修改时间
     */
    private String xgsj ;
    
    /** 
     * 有效标志0无效1有效
     */
    private String yxbz ;

    private boolean display_flag;
    
     /**
      * 子菜单
      */
    private List<Menu> children;
    
	private boolean check;

	public String getGncd_dm() {
		return gncd_dm;
	}

	public void setGncd_dm(String gncd_dm) {
		this.gncd_dm = gncd_dm;
	}

	public String getGncd_mc() {
		return gncd_mc;
	}

	public void setGncd_mc(String gncd_mc) {
		this.gncd_mc = gncd_mc;
	}

	public String getSj_gncd_dm() {
		return sj_gncd_dm;
	}

	public void setSj_gncd_dm(String sj_gncd_dm) {
		this.sj_gncd_dm = sj_gncd_dm;
	}

	public String getSj_gncd_mc() {
		return sj_gncd_mc;
	}

	public void setSj_gncd_mc(String sj_gncd_mc) {
		this.sj_gncd_mc = sj_gncd_mc;
	}

	public Integer getGnfl() {
		return gnfl;
	}

	public void setGnfl(Integer gnfl) {
		this.gnfl = gnfl;
	}

	public Integer getGncd_level() {
		return gncd_level;
	}

	public void setGncd_level(Integer gncd_level) {
		this.gncd_level = gncd_level;
	}

	public Integer getGncd_type() {
		return gncd_type;
	}

	public void setGncd_type(Integer gncd_type) {
		this.gncd_type = gncd_type;
	}

	public String getGncd_img() {
		return gncd_img;
	}

	public void setGncd_img(String gncd_img) {
		this.gncd_img = gncd_img;
	}

	public String getGncd_url() {
		return gncd_url;
	}

	public void setGncd_url(String gncd_url) {
		this.gncd_url = gncd_url;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
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

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}


	public boolean isDisplay_flag() {
		return display_flag;
	}

	public void setDisplay_flag(boolean display_flag) {
		this.display_flag = display_flag;
	}
}