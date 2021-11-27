package com.integration.utils.enums;

/**
 * 日期转换格式
 * 
 * @author dell
 *
 */
public enum DateFmtEnum {

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	FMT_SECODE_HYPHEN("yyyy-MM-dd HH:mm:ss"),
	/**
	 * yyyy/MM/dd HH:mm:ss
	 */
	FMT_SECODE_SLASH("yyyy/MM/dd HH:mm:ss"),
	/**
	 * yyyyMMdd HH:mm:ss
	 */
	FMT_SECODE_NONE("yyyyMMdd HH:mm:ss");
	
	
	private String value;

	public String getValue() {
		return value;
	}

	private DateFmtEnum(String value) {
		this.value = value;
	}
}
