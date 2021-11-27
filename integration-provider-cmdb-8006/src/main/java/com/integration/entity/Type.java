package com.integration.entity;
/**
* @Package: com.integration.entity
* @ClassName: Type
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 大类实体类
*/
public class Type extends BaseEntity {
	/**
	 * id
	 */

	private String id;

	/**
	 * 类别代码
	 */
	private String ci_type_bm;

	/**
	 * 类别名称
	 */

	private String ci_type_mc;

	/**
	 * 类别代码标准值
	 */

	private String ci_type_std_bm;

	/**
	 * 所属目录
	 */

	private String ci_type_dir;

	/**
	 * 上级类别代码
	 */

	private String parent_ci_type_id;

	/**
	 * 类别层级
	 */

	private int ci_type_lv;

	/**
	 * 类别层级路径
	 */

	private String ci_type_path;

	/**
	 * 是否末级
	 */

	private String leaf;

	/**
	 * 类别图表
	 */

	private String ci_type_icon;

	/**
	 * 类别描述
	 */

	private String ci_type_desc;

	/**
	 * 类别颜色
	 */

	private String ci_type_color;

	/**
	 * 排序列
	 */

	private int sort;

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

	private int yxbz;

	/**
	 * 大类下的CI数量
	 */
	private int num;

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

	public String getCi_type_bm() {
		return ci_type_bm;
	}

	public void setCi_type_bm(String ci_type_bm) {
		this.ci_type_bm = ci_type_bm;
	}

	public String getCi_type_mc() {
		return ci_type_mc;
	}

	public void setCi_type_mc(String ci_type_mc) {
		this.ci_type_mc = ci_type_mc;
	}

	public String getCi_type_std_bm() {
		return ci_type_std_bm;
	}

	public void setCi_type_std_bm(String ci_type_std_bm) {
		this.ci_type_std_bm = ci_type_std_bm;
	}

	public String getCi_type_dir() {
		return ci_type_dir;
	}

	public void setCi_type_dir(String ci_type_dir) {
		this.ci_type_dir = ci_type_dir;
	}

	public String getParent_ci_type_id() {
		return parent_ci_type_id;
	}

	public void setParent_ci_type_id(String parent_ci_type_id) {
		this.parent_ci_type_id = parent_ci_type_id;
	}

	public int getCi_type_lv() {
		return ci_type_lv;
	}

	public void setCi_type_lv(int ci_type_lv) {
		this.ci_type_lv = ci_type_lv;
	}

	public String getCi_type_path() {
		return ci_type_path;
	}

	public void setCi_type_path(String ci_type_path) {
		this.ci_type_path = ci_type_path;
	}

	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	public String getCi_type_icon() {
		return ci_type_icon;
	}

	public void setCi_type_icon(String ci_type_icon) {
		this.ci_type_icon = ci_type_icon;
	}

	public String getCi_type_desc() {
		return ci_type_desc;
	}

	public void setCi_type_desc(String ci_type_desc) {
		this.ci_type_desc = ci_type_desc;
	}

	public String getCi_type_color() {
		return ci_type_color;
	}

	public void setCi_type_color(String ci_type_color) {
		this.ci_type_color = ci_type_color;
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

	public int getYxbz() {
		return yxbz;
	}

	public void setYxbz(int yxbz) {
		this.yxbz = yxbz;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getDomain_id() {
		return domain_id;
	}

	public void setDomain_id(String domain_id) {
		this.domain_id = domain_id;
	}

	@Override
	public String toString() {
		return "Type{" + "id='" + id + '\'' + ", ci_type_bm='" + ci_type_bm
				+ '\'' + ", ci_type_mc='" + ci_type_mc + '\''
				+ ", ci_type_std_bm='" + ci_type_std_bm + '\''
				+ ", ci_type_dir='" + ci_type_dir + '\''
				+ ", parent_ci_type_id='" + parent_ci_type_id + '\''
				+ ", ci_type_lv=" + ci_type_lv + ", ci_type_path='"
				+ ci_type_path + '\'' + ", leaf='" + leaf + '\''
				+ ", ci_type_icon='" + ci_type_icon + '\'' + ", ci_type_desc='"
				+ ci_type_desc + '\'' + ", ci_type_color='" + ci_type_color
				+ '\'' + ", sort=" + sort + ", cjr_id=" + cjr_id + ", cjsj='"
				+ cjsj + '\'' + ", xgr_id=" + xgr_id + ", xgsj='" + xgsj + '\''
				+ ", yxbz=" + yxbz + '}';
	}
}
