

package com.integration.entity;


/**
 * @author hbr
 * @date 2018-12-11 05:47:33
 * @version 1.0
 */
 
public class CiKpiTypeInfo {
	
		
	/**
	 * 主键
	 */
    private String id;
    
		
	/**
	 * KPI主键
	 */
    private String kpi_id;
    
		
	/**
	 * 对象类型1:标签;2:CI类别
	 */
    private Integer obj_type;
    
		
	/**
	 * 对象ID
	 */
    private String obj_id;
    
		
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
	
		
    public String getKpi_id(){
		return kpi_id;
	}

	public void setKpi_id (String kpi_id){
		this.kpi_id = kpi_id;
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