package com.integration.entity;
/**
* @Package: com.integration.entity
* @ClassName: DataIndex
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class DataIndex {
	private String id;
	private String ci_id;
	private String attr_id;
	private String type_id;
	private String idx;
	private String cjsj;
	private String xgsj;

	/**
	 * 数据域ID
	 */
	private String domain_id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCi_id() {
		return ci_id;
	}
	public void setCi_id(String ci_id) {
		this.ci_id = ci_id;
	}
	public String getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
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

	public String getDomain_id() {
		return domain_id;
	}

	public void setDomain_id(String domain_id) {
		this.domain_id = domain_id;
	}
}
