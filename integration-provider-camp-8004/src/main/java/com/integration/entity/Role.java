package com.integration.entity;
/**
* @Package: com.integration.entity
* @ClassName: Role
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 角色实体类
*/
public class Role extends BaseEntity {
	/**
	 * 角色代码
	 */
	private String role_dm;
	/**
	 * 角色名称
	 */
	private String role_mc;
	/**
	 * 角色类型
	 */
	private int role_type;
	/**
	 * 角色描述
	 */
	private String role_desc;
	/**
	 * 拍序列
	 */
	private int sort;
	/**
	 * 创建人ID
	 */
	private String cjr_id;
	/**
	 * 创建时间
	 */
	private String cjsj;
	/**
	 * 修改人ID
	 */
	private String xgr_id;
	/**
	 * 修改时间
	 */
	private String xgsj;
	/**
	 * 有效标识
	 */
	private String yxbz;
	private String cjr_name;
	private String xgr_name;

	public String getRole_dm() {
		return role_dm;
	}

	public void setRole_dm(String role_dm) {
		this.role_dm = role_dm;
	}

	public String getRole_mc() {
		return role_mc;
	}

	public void setRole_mc(String role_mc) {
		this.role_mc = role_mc;
	}

	public int getRole_type() {
		return role_type;
	}

	public void setRole_type(int role_type) {
		this.role_type = role_type;
	}

	public String getRole_desc() {
		return role_desc;
	}

	public void setRole_desc(String role_desc) {
		this.role_desc = role_desc;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getCjr_id() {
		return cjr_id;
	}

	public void setCjr_id(String cjr_id) {
		this.cjr_id = cjr_id;
	}

	public String getXgr_id() {
		return xgr_id;
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

	public String getCjr_name() {
		return cjr_name;
	}

	public void setCjr_name(String cjr_name) {
		this.cjr_name = cjr_name;
	}

	public String getXgr_name() {
		return xgr_name;
	}

	public void setXgr_name(String xgr_name) {
		this.xgr_name = xgr_name;
	}

}
