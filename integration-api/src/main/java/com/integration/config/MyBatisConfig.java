package com.integration.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.integration.entity.SqlHandleEntity;

/**
 * mybatis配置
 * 
 * @author dell
 *
 */
@ConfigurationProperties(prefix = "hlkj.mybatis")
@Component
public class MyBatisConfig {
	// 用于进行sql处理的handle集合
	private List<String> handles;
	// dbtype
	private String dmType;
	// sqlClient实现
	private String sqlClient;
	// 对handles的实现
	private List<SqlHandleEntity> impl;
	// 数据库类型 single multi
	private String datasourceType;

	public List<String> getHandles() {
		return handles;
	}

	public void setHandles(List<String> handles) {
		this.handles = handles;
	}

	public String getSqlClient() {
		return sqlClient;
	}

	public void setSqlClient(String sqlClient) {
		this.sqlClient = sqlClient;
	}

	public List<SqlHandleEntity> getImpl() {
		return impl;
	}

	public void setImpl(List<SqlHandleEntity> impl) {
		this.impl = impl;
	}

	public String getDmType() {
		return dmType;
	}

	public void setDmType(String dmType) {
		this.dmType = dmType;
	}

	public String getDatasourceType() {
		return datasourceType;
	}

	public void setDatasourceType(String datasourceType) {
		this.datasourceType = datasourceType;
	}
}
