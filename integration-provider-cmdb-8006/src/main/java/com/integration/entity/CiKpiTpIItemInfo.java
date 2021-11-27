

package com.integration.entity;

/**
 * @author hbr
 * @date 2018-12-14 03:49:58
 * @version 1.0
 */
 
public class CiKpiTpIItemInfo {
	
		
	/**
	 * 主键
	 */
    private String id;
    
		
	/**
	 * 模板ID
	 */
    private String tpl_id;
    
		
	/**
	 * 关联对象类型1:指标;2:CI类别;3:标签
	 */
    private Integer obj_type;
    
		
	/**
	 * 关联对象ID
	 */
    private String obj_id;
    
		
	/**
	 * 关联对象名称
	 */
    private String obj_name;
    
		
	/**
	 * 指标应用类型1:应该;2:可能
	 */
    private Integer kpi_use_type;
    
		
	/**
	 * 创建时间
	 */
    private String cjsj;
    
		
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
	
		
    public String getTpl_id(){
		return tpl_id;
	}

	public void setTpl_id (String tpl_id){
		this.tpl_id = tpl_id;
	}
	
		
    public Integer getObj_type(){
		return obj_type;
	}

	public void setObj_type (Integer obj_type){
		this.obj_type = obj_type;
	}
	
		
    public String getObj_id(){
		return obj_id;
	}

	public void setObj_id (String obj_id){
		this.obj_id = obj_id;
	}
	
		
    public String getObj_name(){
		return obj_name;
	}

	public void setObj_name (String obj_name){
		this.obj_name = obj_name;
	}
	
		
    public Integer getKpi_use_type(){
		return kpi_use_type;
	}

	public void setKpi_use_type (Integer kpi_use_type){
		this.kpi_use_type = kpi_use_type;
	}
	
		
    public String getCjsj(){
		return cjsj;
	}

	public void setCjsj (String cjsj){
		this.cjsj = cjsj;
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