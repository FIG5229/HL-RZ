package com.integration.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 读取配置文件
 * 
 * @author dell
 *
 */
@PropertySource({ "classpath:staticfile.properties" })
@Component
public class AscKEY {
	@Value("${aseKey}")
	String ascKey;

	public String getAscKey() {
		return ascKey;
	}

	public void setAscKey(String ascKey) {
		this.ascKey = ascKey;
	} 



}
