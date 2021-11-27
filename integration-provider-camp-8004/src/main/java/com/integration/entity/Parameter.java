package com.integration.entity;
/**
* @Package: com.integration.entity
* @ClassName: Parameter
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 系统参数
*/
public class Parameter extends BaseEntity{
	/**
	 * 参数ID
	 */
	private String para_id;
	/**
	 * 参数名称
	 */
	private String para_mc;
	/**
	 * 参数类型
	 */
	private String para_type;
	/**
	 * 参数值
	 */
	private String para_data;
	/**
	 * 参数表达式
	 */
	private String para_desc;
	private String is_open;
	/**
	 * 拍序列
	 */
	private String sort;
	/**
	 * 创建人
	 */
	private String cjr_id;
	/**
	 * 创建时间
	 */
	private String cjsj;
	/**
	 * 修改人ID
	 */
	private String xgr_id;
	/**
	 * 修改时间
	 */
	private String xgsj;
	/**
	 * 有效标识
	 */
	private String yxbz;
	/**
	 * 参数标识符
	 */
	private String para_code;

	public String getPara_id() {
		return para_id;
	}

	public void setPara_id(String para_id) {
		this.para_id = para_id;
	}

	public String getPara_mc() {
		return para_mc;
	}

	public void setPara_mc(String para_mc) {
		this.para_mc = para_mc;
	}

	public String getPara_type() {
		return para_type;
	}

	public void setPara_type(String para_type) {
		this.para_type = para_type;
	}

	public String getPara_data() {
		return para_data;
	}

	public void setPara_data(String para_data) {
		this.para_data = para_data;
	}

	public String getPara_desc() {
		return para_desc;
	}

	public void setPara_desc(String para_desc) {
		this.para_desc = para_desc;
	}

	public String getIs_open() {
		return is_open;
	}

	public void setIs_open(String is_open) {
		this.is_open = is_open;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
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

	public String getPara_code() {
		return para_code;
	}

	public void setPara_code(String para_code) {
		this.para_code = para_code;
	}
}
