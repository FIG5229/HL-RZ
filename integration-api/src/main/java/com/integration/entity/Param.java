package com.integration.entity;

import java.util.List;
import java.util.Map;

public class Param {
	
	private String dbtype;				//数据库类型
	private String database;			//库名
	private String table;				//表名
	private String moshi;				//模式名
	private List<String> property;		//字段名
	private Map<String,String> tiaojian;	//判断条件
	private Map<String,String> paixu;		//排序条件
	private String sql;						//SQL语句
	private String lable;					//D文件标签名
	private String interfaceType;			//接口类型
	private String tableName;				//最终转换格式表名称
	
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public String getDbtype() {
		return dbtype;
	}
	public void setDbtype(String dbtype) {
		this.dbtype = dbtype;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getMoshi() {
		return moshi;
	}
	public void setMoshi(String moshi) {
		this.moshi = moshi;
	}
	public List<String> getProperty() {
		return property;
	}
	public void setProperty(List<String> property) {
		this.property = property;
	}
	public Map<String, String> getTiaojian() {
		return tiaojian;
	}
	public void setTiaojian(Map<String, String> tiaojian) {
		this.tiaojian = tiaojian;
	}
	public Map<String, String> getPaixu() {
		return paixu;
	}
	public void setPaixu(Map<String, String> paixu) {
		this.paixu = paixu;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	
	

	
	

}
