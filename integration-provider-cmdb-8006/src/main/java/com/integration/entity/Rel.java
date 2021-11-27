

package com.integration.entity;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @date 2018-11-28 09:27:13
 * @version 1.0
 */
 
public class Rel extends BaseEntity {
	
		
	
	/**
	 * ID
	 */
    private String id;
    
		
	/**
	 * 关系代码
	 */
    private String line_bm;
    
		
	/**
	 * 关系名称
	 */
    private String line_name;
    
		
	/**
	 * 关系代码标准值
	 */
    private String line_std_bm;
    
		
	/**
	 * 关系描述
	 */
    private String line_desc;
    
		
	/**
	 * 上级关系ID
	 */
    private String parent_line_id;
    
		
	/**
	 * 关系层级
	 */
    private Integer line_lvl;
    
		
	/**
	 * 关系路径
	 */
    private String line_path;
    
		
	/**
	 * 排序列
	 */
    private Integer sort;
    
		
	/**
	 * 是否归集0＝不归集1＝归集（源目标）2＝归集（目标源）
	 */
    private Integer line_cost;
    
		
	/**
	 * 关系样式
	 */
    private Integer line_style;
    
		
	/**
	 * 关系宽度
	 */
    private Integer line_width;
    
		
	/**
	 * 关系颜色
	 */
    private String line_color;
    
		
	/**
	 * 关系箭头
	 */
    private Integer line_arror;
    
		
	/**
	 * 关系动态效果
	 */
    private Integer line_anime;
    
		
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


	public String getLine_bm() {
		return line_bm;
	}


	public void setLine_bm(String line_bm) {
		this.line_bm = line_bm;
	}


	public String getLine_name() {
		return line_name;
	}


	public void setLine_name(String line_name) {
		this.line_name = line_name;
	}


	public String getLine_std_bm() {
		return line_std_bm;
	}


	public void setLine_std_bm(String line_std_bm) {
		this.line_std_bm = line_std_bm;
	}


	public String getLine_desc() {
		return line_desc;
	}


	public void setLine_desc(String line_desc) {
		this.line_desc = line_desc;
	}


	public String getParent_line_id() {
		return parent_line_id;
	}


	public void setParent_line_id(String parent_line_id) {
		this.parent_line_id = parent_line_id;
	}


	public Integer getLine_lvl() {
		return line_lvl;
	}


	public void setLine_lvl(Integer line_lvl) {
		this.line_lvl = line_lvl;
	}


	public String getLine_path() {
		return line_path;
	}


	public void setLine_path(String line_path) {
		this.line_path = line_path;
	}


	public Integer getSort() {
		return sort;
	}


	public void setSort(Integer sort) {
		this.sort = sort;
	}


	public Integer getLine_cost() {
		return line_cost;
	}


	public void setLine_cost(Integer line_cost) {
		this.line_cost = line_cost;
	}


	public Integer getLine_style() {
		return line_style;
	}


	public void setLine_style(Integer line_style) {
		this.line_style = line_style;
	}


	public Integer getLine_width() {
		return line_width;
	}


	public void setLine_width(Integer line_width) {
		this.line_width = line_width;
	}


	public String getLine_color() {
		return line_color;
	}


	public void setLine_color(String line_color) {
		this.line_color = line_color;
	}


	public Integer getLine_arror() {
		return line_arror;
	}


	public void setLine_arror(Integer line_arror) {
		this.line_arror = line_arror;
	}


	public Integer getLine_anime() {
		return line_anime;
	}


	public void setLine_anime(Integer line_anime) {
		this.line_anime = line_anime;
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


	public Integer getYxbz() {
		return yxbz;
	}


	public void setYxbz(Integer yxbz) {
		this.yxbz = yxbz;
	}

	public String getDomain_id() {
		return domain_id;
	}

	public void setDomain_id(String domain_id) {
		this.domain_id = domain_id;
	}
}