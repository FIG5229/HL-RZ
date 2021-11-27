package com.integration.entity;

/**
* @Package: com.integration.entity
* @ClassName: Organiza
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 组织管理实体类
*/
public class Organiza extends BaseEntity{
	private String  id;
	private String zzjg_mc;
	private String sj_id;
	private String cjr_id;
	private String cjsj;
	private String xgr_id;
	private String xgsj;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getZzjg_mc() {
		return zzjg_mc;
	}

	public void setZzjg_mc(String zzjg_mc) {
		this.zzjg_mc = zzjg_mc;
	}

	public String getSj_id() {
		return sj_id;
	}

	public void setSj_id(String sj_id) {
		this.sj_id = sj_id;
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

	@Override
	public String toString() {
		return "Organiza{" +
				"id='" + id + '\'' +
				", zzjg_mc='" + zzjg_mc + '\'' +
				", sj_id='" + sj_id + '\'' +
				", cjr_id='" + cjr_id + '\'' +
				", cjsj='" + cjsj + '\'' +
				", xgr_id='" + xgr_id + '\'' +
				", xgsj='" + xgsj + '\'' +
				'}';
	}
}
