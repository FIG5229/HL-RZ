package com.integration.entity;

import java.util.Map;

/**
 * sqlhandle impl bean
 * 
 * @author dell
 *
 */
public class SqlHandleEntity {

	// sqlhandle的简称，同handles中的一致
	private String alias;
	// sqlhandle接口的具体class
	private String fullname;
	// sqlhandle的实现类class
	private String def;
	// 针对不同的数据库的实现
	private Map<String, String> dbType;
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getDef() {
		return def;
	}
	public void setDef(String def) {
		this.def = def;
	}
	public Map<String, String> getDbType() {
		return dbType;
	}
	public void setDbType(Map<String, String> dbType) {
		this.dbType = dbType;
	}
	public SqlHandleEntity(String alias, String fullname, String def, Map<String, String> dbType) {
		super();
		this.alias = alias;
		this.fullname = fullname;
		this.def = def;
		this.dbType = dbType;
	}
	public SqlHandleEntity() {
		super();
	}
}
