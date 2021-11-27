package com.integration.entity;

import java.io.Serializable;

/**
* @Package: com.integration.entity
* @ClassName: MenuRes
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 菜单资源实体类
*/
public class MenuRes implements Serializable {

    /** 
     * 资源代码
     */
    private String res_dm ;
    
    /** 
     * 资源名称
     */
    private String res_mc ;
    
    /** 
     * 功能菜单代码
     */
    private String gncd_dm ;
    
    
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
     * 有效标志
     */
    private String yxbz ;
    
    /**
     * 创建人姓名
     */
    private String cjrName;
    
    /**
     * 修改人姓名
     */
    private String xgrName;
    
    public String getCjrName() {
		return cjrName;
	}

	public void setCjrName(String cjrName) {
		this.cjrName = cjrName;
	}

	public String getXgrName() {
		return xgrName;
	}

	public void setXgrName(String xgrName) {
		this.xgrName = xgrName;
	}

	public Integer getRes_type() {
		return res_type;
	}

	public void setRes_type(Integer res_type) {
		this.res_type = res_type;
	}

	public String getRes_url() {
		return res_url;
	}

	public void setRes_url(String res_url) {
		this.res_url = res_url;
	}

	private Integer res_type;
    
    private String res_url;


	public String getRes_dm() {
		return res_dm;
	}

	public void setRes_dm(String res_dm) {
		this.res_dm = res_dm;
	}

	public String getRes_mc() {
		return res_mc;
	}

	public void setRes_mc(String res_mc) {
		this.res_mc = res_mc;
	}

	public String getGncd_dm() {
		return gncd_dm;
	}

	public void setGncd_dm(String gncd_dm) {
		this.gncd_dm = gncd_dm;
	}

	public String getXgr_id() {
		return xgr_id;
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

	public void setXgr_id(String xgr_id) {
		this.xgr_id = xgr_id;
	}

	public String getCjsj() {
		return cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
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


    
   

 
 
}