package com.integration.entity;

import java.util.List;
/**
* @Package: com.integration.entity
* @ClassName: TypeInfoList
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 类数据列表实体类
*/
public class TypeInfoList {
	/**
	 * id
	 */

	private String id;

	/**
	 * 类别名称
	 */

	private String ci_type_mc;

	private String ci_type_icon;

	private int num;
	
	private String imageFullName;

	/**
	 * ci信息集合
	 */
	private List<Info> infoList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCi_type_mc() {
		return ci_type_mc;
	}

	public void setCi_type_mc(String ci_type_mc) {
		this.ci_type_mc = ci_type_mc;
	}

	public List<Info> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<Info> infoList) {
		this.infoList = infoList;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getCi_type_icon() {
		return ci_type_icon;
	}

	public void setCi_type_icon(String ci_type_icon) {
		this.ci_type_icon = ci_type_icon;
	}

	public String getImageFullName() {
		return imageFullName;
	}

	public void setImageFullName(String imageFullName) {
		this.imageFullName = imageFullName;
	}

	
	
	

}
