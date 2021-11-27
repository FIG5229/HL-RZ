package com.integration.entity;
/**
* @Package: com.integration.entity
* @ClassName: RoleMenu
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 角色菜单实体类
*/
public class RoleMenu extends BaseEntity{
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 角色ID
	 */
	private String role_dm;
	/**
	 * 功能菜单ID
	 */
	private String gncd_dm;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRole_dm() {
		return role_dm;
	}
	public void setRole_dm(String role_dm) {
		this.role_dm = role_dm;
	}

	public String getGncd_dm() {
		return gncd_dm;
	}
	public void setGncd_dm(String gncd_dm) {
		this.gncd_dm = gncd_dm;
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
	
	

}
