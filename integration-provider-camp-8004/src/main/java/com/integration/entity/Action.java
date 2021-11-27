package com.integration.entity;
/**
* @Package: com.integration.entity
* @ClassName: Action
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 操作日志实体类
*/
public class Action extends BaseEntity {
	/**
	 * 日志时间
	 */
	private String log_time;
	/**
	 * 用户ID
	 */
	private String user_id;
	/**
	 * 用户登录名
	 */
	private String czry_dldm;
	 /**
	  * 用户名称
	  */
	private String czry_mc;
	 /**
	  * 模块名称
	  */
	private String op_name;
	/**
	 * 访问路径
	 */
	private String op_path;
	/**
	 *模块描述
	 */
	private String op_desc;
	/**
	 * 是否成功true：成功false：失败
	 */
	private boolean is_status;
	/**
	 * 前端参数
	 */
	private String op_param;
	/**
	 * 返回结果
	 */
	private String op_result;
	/**
	 * 访问类名包括路径
	 */
	private String op_class;
	/**
	 * 访问方法
	 */
	private String op_method;
	/**
	 * 创建时间
	 */
	private String cjsj;
	public String getLog_time() {
		return log_time;
	}
	public void setLog_time(String log_time) {
		this.log_time = log_time;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCzry_dldm() {
		return czry_dldm;
	}
	public void setCzry_dldm(String czry_dldm) {
		this.czry_dldm = czry_dldm;
	}
	public String getCzry_mc() {
		return czry_mc;
	}
	public void setCzry_mc(String czry_mc) {
		this.czry_mc = czry_mc;
	}
	public String getOp_name() {
		return op_name;
	}
	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}
	public String getOp_path() {
		return op_path;
	}
	public void setOp_path(String op_path) {
		this.op_path = op_path;
	}
	public String getOp_desc() {
		return op_desc;
	}
	public void setOp_desc(String op_desc) {
		this.op_desc = op_desc;
	}

	public String getOp_param() {
		return op_param;
	}
	public void setOp_param(String op_param) {
		this.op_param = op_param;
	}
	public String getOp_result() {
		return op_result;
	}
	public void setOp_result(String op_result) {
		this.op_result = op_result;
	}
	public String getOp_class() {
		return op_class;
	}
	public void setOp_class(String op_class) {
		this.op_class = op_class;
	}
	public String getOp_method() {
		return op_method;
	}
	public void setOp_method(String op_method) {
		this.op_method = op_method;
	}
	public String getCjsj() {
		return cjsj;
	}
	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}
	public boolean isIs_status() {
		return is_status;
	}
	public void setIs_status(boolean is_status) {
		this.is_status = is_status;
	}
	
	
	
	
	
}
