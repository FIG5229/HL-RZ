package com.integration.entity;

public class Dict extends BaseEntity {
	/**
	 * 主键
	 */
	private String dict_id;
	/**
	 * 字典ID
	 */
	private String dict_bm;
	/**
	 * 字典名称
	 */
	private String dict_name;
	/**
	 * 上级字典ID
	 */
	private String sj_id;
	/**
	 * 功能分类 0枝干 1叶子
	 */
	private int gnfl;
	/**
	 * 拍序列
	 */
	private int sort;
	/**
	 * 创建人ID
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
	 * 有效标识 0无效 1有效
	 */
	private String yxbz;
	/**
	 *
	 */
	private String coutent;

	public String getDict_id() {
		return dict_id;
	}

	public void setDict_id(String dict_id) {
		this.dict_id = dict_id;
	}

	public String getDict_bm() {
		return dict_bm;
	}

	public void setDict_bm(String dict_bm) {
		this.dict_bm = dict_bm;
	}

	public String getDict_name() {
		return dict_name;
	}

	public void setDict_name(String dict_name) {
		this.dict_name = dict_name;
	}

	public String getSj_id() {
		return sj_id;
	}

	public void setSj_id(String sj_id) {
		this.sj_id = sj_id;
	}

	public int getGnfl() {
		return gnfl;
	}

	public void setGnfl(int gnfl) {
		this.gnfl = gnfl;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
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

	public String getCoutent() {
		return coutent;
	}

	public void setCoutent(String coutent) {
		this.coutent = coutent;
	}

}
