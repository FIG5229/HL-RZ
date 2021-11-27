

package com.integration.entity;

/**
 * @author hbr
 * @date 2018-12-14 03:49:01
 * @version 1.0
 */
public class CiKpiTplInfo {
	
		
	/**
	 * 主键
	 */
    private String id;
    
		
	/**
	 * 模板名称
	 */
    private String tpl_name;
    
		
	/**
	 * 模板别名
	 */
    private String tpl_alias;
    
		
	/**
	 * 模板描述
	 */
    private String tpl_desc;
    
		
	/**
	 * 搜索字段
	 */
    private String idx_field;
    
		
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
    private Integer yxbz;
    
		
		
    public String getId(){
		return id;
	}

	public void setId (String id){
		this.id = id;
	}
	
		
    public String getTpl_name(){
		return tpl_name;
	}

	public void setTpl_name (String tpl_name){
		this.tpl_name = tpl_name;
	}
	
		
    public String getTpl_alias(){
		return tpl_alias;
	}

	public void setTpl_alias (String tpl_alias){
		this.tpl_alias = tpl_alias;
	}
	
		
    public String getTpl_desc(){
		return tpl_desc;
	}

	public void setTpl_desc (String tpl_desc){
		this.tpl_desc = tpl_desc;
	}
	
		
    public String getIdx_field(){
		return idx_field;
	}

	public void setIdx_field (String idx_field){
		this.idx_field = idx_field;
	}
	
		
    public String getCjr_id(){
		return cjr_id;
	}

	public void setCjr_id (String cjr_id){
		this.cjr_id = cjr_id;
	}
	
		
    public String getCjsj(){
		return cjsj;
	}

	public void setCjsj (String cjsj){
		this.cjsj = cjsj;
	}
	
		
    public String getXgr_id(){
		return xgr_id;
	}

	public void setXgr_id (String xgr_id){
		this.xgr_id = xgr_id;
	}
	
		
    public String getXgsj(){
		return xgsj;
	}

	public void setXgsj (String xgsj){
		this.xgsj = xgsj;
	}
	
		
    public Integer getYxbz(){
		return yxbz;
	}

	public void setYxbz (Integer yxbz){
		this.yxbz = yxbz;
	}
	
	
}