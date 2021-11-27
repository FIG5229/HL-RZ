

package com.integration.entitypmv;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @date 2019-03-04 03:16:32
 * @version 1.0
 */
 
public class PmvPerformanceCurrentInfo {
	
		
	/**
	 * 主键
	 */
    private String id;
    
		
	/**
	 * 数据来源
	 */
    private String data_dource;
    
		
	/**
	 * CIID
	 */
    private String ci_id;
    
		
	/**
	 * CI名称
	 */
    private String ci_name;
    
		
	/**
	 * CI类型ID
	 */
    private String ci_type_id;
    
		
	/**
	 * CI类型名称
	 */
    private String ci_type_name;
    
		
	/**
	 * KPIID
	 */
    private String kpi_id;
    
		
	/**
	 * KPI名称
	 */
    private String kpi_name;
    
		
	/**
	 * 性能值
	 */
    private String val;
    
		
	/**
	 * 性能参数
	 */
    private String val_parem;
    
		
	/**
	 * 计算值
	 */
    private String val_cal;
    
		
	/**
	 * 时间
	 */
    private String occ_time;
    
		
	/**
	 * 创建时间
	 */
    private String cjsj;
    
		
	/**
	 * 修改时间
	 */
    private String xgsj;
    
		
		
    public String getId(){
		return id;
	}

	public void setId (String id){
		this.id = id;
	}
	
		
    public String getData_dource(){
		return data_dource;
	}

	public void setData_dource (String data_dource){
		this.data_dource = data_dource;
	}
	
		
    public String getCi_id(){
		return ci_id;
	}

	public void setCi_id (String ci_id){
		this.ci_id = ci_id;
	}
	
		
    public String getCi_name(){
		return ci_name;
	}

	public void setCi_name (String ci_name){
		this.ci_name = ci_name;
	}
	
		
    public String getCi_type_id(){
		return ci_type_id;
	}

	public void setCi_type_id (String ci_type_id){
		this.ci_type_id = ci_type_id;
	}
	
		
    public String getCi_type_name(){
		return ci_type_name;
	}

	public void setCi_type_name (String ci_type_name){
		this.ci_type_name = ci_type_name;
	}
	
		
    public String getKpi_id(){
		return kpi_id;
	}

	public void setKpi_id (String kpi_id){
		this.kpi_id = kpi_id;
	}
	
		
    public String getKpi_name(){
		return kpi_name;
	}

	public void setKpi_name (String kpi_name){
		this.kpi_name = kpi_name;
	}
	
		
    public String getVal(){
		return val;
	}

	public void setVal (String val){
		this.val = val;
	}
	
		
    public String getVal_parem(){
		return val_parem;
	}

	public void setVal_parem (String val_parem){
		this.val_parem = val_parem;
	}
	
		
    public String getVal_cal(){
		return val_cal;
	}

	public void setVal_cal (String val_cal){
		this.val_cal = val_cal;
	}
	
		
    public String getOcc_time(){
		return occ_time;
	}

	public void setOcc_time (String occ_time){
		this.occ_time = occ_time;
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
	
	
}