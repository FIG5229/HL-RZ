package com.integration.entity;

import java.util.List;

import com.integration.integration_provider_8006;
import com.integration.entity.vo.TreeVO;
/**
* @Package: com.integration.entity
* @ClassName: Tree
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public class Tree extends TreeVO {
	
	/**
	 * id
	 */
	private String id;	
	
	/**
	 * 父节点ID
	 */
	private String pid;
	
	/**
	 * 名称
	 */
	private String name;
	
	/***
	 * 图标
	 */
	private String icon;
	
	/**
	 * 类型 目录还是大类
	 */
	private String type;
	
	/**
	 * 排序列
	 */
	private int sort;
	
	/**
	 * 子节点
	 */
	private List<Tree> children;
	
	private int count;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public List<Tree> getChildren() {
		return children;
	}
	public void setChildren(List<Tree> children) {
		this.children = children;
	}
	
	
	
	

}
