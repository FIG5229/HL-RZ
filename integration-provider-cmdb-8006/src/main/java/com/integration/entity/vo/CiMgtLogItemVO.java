package com.integration.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 

* @author yangXiChuan

* @version 创建时间：2019年10月29日 下午3:53:42 

* ci变更日志的变更内容

*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CiMgtLogItemVO {
	
	public static final String ADD = "1";
	
	public static final String DEL = "2";
	
	public static final String EDIT = "3";
	
	public static final String SEARCH = "4";
	
	
	/**
	 * 属性id
	 */
	private String id;
	/**
	 * 属性名称
	 */
	private String name;
	/**
	 * 修改之前的值
	 */
	private String befor;
	/**
	 * 修改之后的值
	 */
	private String after;
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
	public String getBefor() {
		return befor;
	}
	public void setBefor(String befor) {
		this.befor = befor;
	}
	public String getAfter() {
		return after;
	}
	public void setAfter(String after) {
		this.after = after;
	}

	
	
	
	
	
}
