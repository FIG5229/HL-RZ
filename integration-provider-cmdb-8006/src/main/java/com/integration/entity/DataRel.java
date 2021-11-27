

package com.integration.entity;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @date 2018-11-28 09:13:28
 * @version 1.0
 */
 
public class DataRel  extends BaseEntity{
	
		
	/**
	 * 主键
	 */
    private String id;
    
		
	/**
	 * 关系ID
	 */
    private String rel_id;
    
		
	/**
	 * 关系名称
	 */
    private String rel_name;
    
		
	/**
	 * 关系描述
	 */
    private String rel_desc;
    
		
	/**
	 * 来源
	 */
    private Integer source_id;
    
		
	/**
	 * 所有者
	 */
    private Integer owenr_id;
    
		
	/**
	 * 所属组织
	 */
    private Integer org_id;
    
		
	/**
	 * 源CIID
	 */
    private String source_ci_id;
    
		
	/**
	 * 源CI编码
	 */
    private String source_ci_bm;

	private String source_ci_name;
    
		
	/**
	 * 源CI类别
	 */
    private String source_type_bm;
    
    private String source_type_id;
    
		
	/**
	 * 目标CIID
	 */
    private String target_ci_id;
    
		
	/**
	 * 目标CI编码
	 */
    private String target_ci_bm;

	private String target_ci_name;
    
		
	/**
	 * 目标CI类别
	 */
    private String target_type_bm;
    
    private String target_type_id;
		
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
     * 源ci字段
     */
    private String source_ci_code;
    /**
     * 目标ci字段
     */
    private String target_ci_code;

	/**
	 * 数据域ID
	 */
	private String domain_id;
    
    
   


	public String getSource_type_id() {
		return source_type_id;
	}


	public void setSource_type_id(String source_type_id) {
		this.source_type_id = source_type_id;
	}


	public String getTarget_type_id() {
		return target_type_id;
	}


	public void setTarget_type_id(String target_type_id) {
		this.target_type_id = target_type_id;
	}


	public String getSource_ci_code() {
		return source_ci_code;
	}


	public void setSource_ci_code(String source_ci_code) {
		this.source_ci_code = source_ci_code;
	}


	public String getTarget_ci_code() {
		return target_ci_code;
	}


	public void setTarget_ci_code(String target_ci_code) {
		this.target_ci_code = target_ci_code;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getRel_id() {
		return rel_id;
	}


	public void setRel_id(String rel_id) {
		this.rel_id = rel_id;
	}


	public String getRel_name() {
		return rel_name;
	}


	public void setRel_name(String rel_name) {
		this.rel_name = rel_name;
	}


	public String getRel_desc() {
		return rel_desc;
	}


	public void setRel_desc(String rel_desc) {
		this.rel_desc = rel_desc;
	}


	public Integer getSource_id() {
		return source_id;
	}


	public void setSource_id(Integer source_id) {
		this.source_id = source_id;
	}


	public Integer getOwenr_id() {
		return owenr_id;
	}


	public void setOwenr_id(Integer owenr_id) {
		this.owenr_id = owenr_id;
	}


	public Integer getOrg_id() {
		return org_id;
	}


	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}


	public String getSource_ci_id() {
		return source_ci_id;
	}


	public void setSource_ci_id(String source_ci_id) {
		this.source_ci_id = source_ci_id;
	}


	public String getSource_ci_bm() {
		return source_ci_bm;
	}


	public void setSource_ci_bm(String source_ci_bm) {
		this.source_ci_bm = source_ci_bm;
	}


	public String getSource_type_bm() {
		return source_type_bm;
	}


	public void setSource_type_bm(String source_type_bm) {
		this.source_type_bm = source_type_bm;
	}


	public String getTarget_ci_id() {
		return target_ci_id;
	}


	public void setTarget_ci_id(String target_ci_id) {
		this.target_ci_id = target_ci_id;
	}


	public String getTarget_ci_bm() {
		return target_ci_bm;
	}


	public void setTarget_ci_bm(String target_ci_bm) {
		this.target_ci_bm = target_ci_bm;
	}


	public String getTarget_type_bm() {
		return target_type_bm;
	}


	public void setTarget_type_bm(String target_type_bm) {
		this.target_type_bm = target_type_bm;
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

	public String getDomain_id() {
		return domain_id;
	}

	public void setDomain_id(String domain_id) {
		this.domain_id = domain_id;
	}

	public String getSource_ci_name() {
		return source_ci_name;
	}

	public void setSource_ci_name(String source_ci_name) {
		this.source_ci_name = source_ci_name;
	}

	public String getTarget_ci_name() {
		return target_ci_name;
	}

	public void setTarget_ci_name(String target_ci_name) {
		this.target_ci_name = target_ci_name;
	}

	@Override
	public String toString() {
		return "DataRel [id=" + id + ", rel_id=" + rel_id + ", rel_name=" + rel_name + ", rel_desc=" + rel_desc
				+ ", source_id=" + source_id + ", owenr_id=" + owenr_id + ", org_id=" + org_id + ", source_ci_id="
				+ source_ci_id + ", source_ci_bm=" + source_ci_bm + ", source_type_bm=" + source_type_bm
				+ ", target_ci_id=" + target_ci_id + ", target_ci_bm=" + target_ci_bm + ", target_type_bm="
				+ target_type_bm + ", cjr_id=" + cjr_id + ", cjsj=" + cjsj + ", xgr_id=" + xgr_id + ", xgsj=" + xgsj
				+ ", yxbz=" + yxbz + ", source_ci_code=" + source_ci_code + ", target_ci_code=" + target_ci_code + "]";
	}
}