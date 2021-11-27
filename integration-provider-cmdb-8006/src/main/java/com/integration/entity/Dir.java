package com.integration.entity;

import java.util.List;
/**
* @Package: com.integration.entity
* @ClassName: Dir
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 目录管理
*/
public class Dir extends BaseEntity{
	
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 目录名称
	 */	
	private String dir_name;
	
	/**
	 * 目录类型1:CI类别  2:CI关系  3图标
	 */	
	private int dir_type;
	
	/**
	 * 上级目录ID
	 */
	private String parent_dir_id;
	
	/**
	 * 目录层级
	 */
	private int dir_lvl;
	
	/**
	 * 目录路径
	 */
	private String dir_path;
	
	/**
	 * 排序列
	 */
	private int sort;
	
	/**
	 * 是否末级
	 */
	private int is_leaf;
	
	/**
	 * 目录图标
	 */
	private String dir_icon;
	
	/**
	 * 目录颜色
	 */
	private String dir_color;
	
	/**
	 * 目录描述
	 */
	private String dir_desc;
	
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
	 * 有效标志
	 */
	private int yxbz;

	/**
	 * 数据域ID
	 */
	private String domain_id;

	private List<Dir> children;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDir_name() {
		return dir_name;
	}
	public void setDir_name(String dir_name) {
		this.dir_name = dir_name;
	}
	public int getDir_type() {
		return dir_type;
	}
	public void setDir_type(int dir_type) {
		this.dir_type = dir_type;
	}
	public String getParent_dir_id() {
		return parent_dir_id;
	}
	public void setParent_dir_id(String parent_dir_id) {
		this.parent_dir_id = parent_dir_id;
	}
	public int getDir_lvl() {
		return dir_lvl;
	}
	public void setDir_lvl(int dir_lvl) {
		this.dir_lvl = dir_lvl;
	}
	public String getDir_path() {
		return dir_path;
	}
	public void setDir_path(String dir_path) {
		this.dir_path = dir_path;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getIs_leaf() {
		return is_leaf;
	}
	public void setIs_leaf(int is_leaf) {
		this.is_leaf = is_leaf;
	}
	public String getDir_icon() {
		return dir_icon;
	}
	public void setDir_icon(String dir_icon) {
		this.dir_icon = dir_icon;
	}
	public String getDir_color() {
		return dir_color;
	}
	public void setDir_color(String dir_color) {
		this.dir_color = dir_color;
	}
	public String getDir_desc() {
		return dir_desc;
	}
	public void setDir_desc(String dir_desc) {
		this.dir_desc = dir_desc;
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
	public int getYxbz() {
		return yxbz;
	}
	public void setYxbz(int yxbz) {
		this.yxbz = yxbz;
	}
	public List<Dir> getChildren() {
		return children;
	}
	public void setChildren(List<Dir> children) {
		this.children = children;
	}

	public String getDomain_id() {
		return domain_id;
	}

	public void setDomain_id(String domain_id) {
		this.domain_id = domain_id;
	}
}
