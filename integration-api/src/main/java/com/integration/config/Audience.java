package com.integration.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.integration.entity.BaseEntity;

/**
 * token解密类
 * @author dell
 *
 */
@SuppressWarnings("serial")
@ConfigurationProperties(prefix = "audience")
@Component(value="tokenAudience")
public class Audience extends BaseEntity{
	private String clientId;
	private String base64Secret;
	private String name;
	private int expiresSecond;
	private int ddlogin;

	private int dfilter;

	/**
	 * token前缀
	 */
	private String prefix = "token:";
	/**
	 * 同时允许登录个数
	 */
	private int max = 3;

	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getBase64Secret() {
		return base64Secret;
	}
	public void setBase64Secret(String base64Secret) {
		this.base64Secret = base64Secret;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getExpiresSecond() {
		return expiresSecond;
	}
	public void setExpiresSecond(int expiresSecond) {
		this.expiresSecond = expiresSecond;
	}
	public int getDdlogin() {
		return ddlogin;
	}
	public void setDdlogin(int ddlogin) {
		this.ddlogin = ddlogin;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getDfilter() {
		return dfilter;
	}

	public void setDfilter(int dfilter) {
		this.dfilter = dfilter;
	}
}
