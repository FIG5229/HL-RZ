package com.integration.entity;

/**
* @Package: com.integration.entity
* @ClassName: CzryRole
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 用户角色关系实体类
*/
public class CzryRole extends BaseEntity{
		private String id;
		private String czry_id;
		private String role_dm;
		private String cjr_id;
		private String cjsj;
		private String xgr_id;
		private String xgsj;
		private String roleName;
		
		public String getRoleName() {
			return roleName;
		}
		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}


	public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getCzry_id() {
			return czry_id;
		}
		public void setCzry_id(String czry_id) {
			this.czry_id = czry_id;
		}
	public String getRole_dm() {
		return role_dm;
	}

	public void setRole_dm(String role_dm) {
		this.role_dm = role_dm;
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
