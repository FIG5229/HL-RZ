package com.integration.entity;

/**
* @Package: com.integration.entity
* @ClassName: Info
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 台账信息实体类
*/
public class Info extends BaseEntity {

	/**
	 * id
	 */
	private String id;

	/**
	 * ci编码
	 */
	private String ci_bm;

	private String ciBm;
	private String ciDesc;
	private String ciId;

	private String ciCode;

	private String ci_code;

	private String ci_name;

	private String ciName;
	/**
	 * ci描述
	 */
	private String ci_desc;

	/**
	 * 所属类别
	 */
	private String ci_type_id;

	/**
	 * 所属类别编码
	 */
	private String ci_type_bm;

	/**
	 * 来源
	 */
	private int source_id;

	/**
	 * 所有者
	 */
	private int owner_id;

	/**
	 * 所属组织
	 */
	private int org_id;

	/**
	 * CI版本
	 */
	private String ci_version;

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
	private String yxbz;

	/**
	 * ci属性值
	 */
	private String attrs_str;

	/**
	 * 数据域ID
	 */
	private String domain_id;

	private String ci_type_icon;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCi_bm() {
		return ci_bm;
	}

	public void setCi_bm(String ci_bm) {
		this.ci_bm = ci_bm;
	}

	public String getCi_desc() {
		return ci_desc;
	}

	public void setCi_desc(String ci_desc) {
		this.ci_desc = ci_desc;
	}

	public String getCi_type_id() {
		return ci_type_id;
	}

	public void setCi_type_id(String ci_type_id) {
		this.ci_type_id = ci_type_id;
	}

	public int getSource_id() {
		return source_id;
	}

	public void setSource_id(int source_id) {
		this.source_id = source_id;
	}

	public int getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
	}

	public int getOrg_id() {
		return org_id;
	}

	public void setOrg_id(int org_id) {
		this.org_id = org_id;
	}

	public String getCi_version() {
		return ci_version;
	}

	public void setCi_version(String ci_version) {
		this.ci_version = ci_version;
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

	public String getCi_type_bm() {
		return ci_type_bm;
	}

	public void setCi_type_bm(String ci_type_bm) {
		this.ci_type_bm = ci_type_bm;
	}

	public String getCi_type_icon() {
		return ci_type_icon;
	}

	public void setCi_type_icon(String ci_type_icon) {
		this.ci_type_icon = ci_type_icon;
	}

	public String getCiBm() {
		return ciBm;
	}

	public void setCiBm(String ciBm) {
		this.ciBm = ciBm;
	}

	public String getCiDesc() {
		return ciDesc;
	}

	public void setCiDesc(String ciDesc) {
		this.ciDesc = ciDesc;
	}

	public String getCiId() {
		return ciId;
	}

	public void setCiId(String ciId) {
		this.ciId = ciId;
	}

	public String getDomain_id() {
		return domain_id;
	}

	public void setDomain_id(String domain_id) {
		this.domain_id = domain_id;
	}

	public String getAttrs_str() {
		return attrs_str;
	}

	public void setAttrs_str(String attrs_str) {
		this.attrs_str = attrs_str;
	}

	public String getCiCode() {
		return ciCode;
	}

	public void setCiCode(String ciCode) {
		this.ciCode = ciCode;
	}

	public String getCiName() {
		return ciName;
	}

	public void setCiName(String ciName) {
		this.ciName = ciName;
	}

	public String getCi_code() {
		return ci_code;
	}

	public void setCi_code(String ci_code) {
		this.ci_code = ci_code;
	}

	public String getCi_name() {
		return ci_name;
	}

	public void setCi_name(String ci_name) {
		this.ci_name = ci_name;
	}
}
