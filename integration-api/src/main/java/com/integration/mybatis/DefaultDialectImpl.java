package com.integration.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.integration.entity.SqlHandleEntity;

/**
 * 本地化
 * 
 * @author dell
 *
 */
public class DefaultDialectImpl {

	// 默认的sqlHandle
	private static final List<SqlHandleEntity> DEFAULT_SQLHANDLE = new ArrayList<>();
	// 默认的分页实现
	private static final Map<String, String> DEFAULT_PAGEHANDLE = new HashMap<>();
	// 默认的dbType
	private static final String DEFAULT_DMTYPE = "dm7";
	// 默认的sqlClient实现
	private static final String DEFAULT_CLIENT = "com.integration.mybatis.impl.ConfigSqlClient";
	//  默认的数据库parse映射，比如dm数据库映射为mysql数据库处理
	private static final Map<String, String> DB_PARSER_MAPPING = new HashMap<>();
	// 默认的使用的数据库的类型为1种
	private static final String DATASOURCE_TYPE = "single";
	// 数据库的关键字
	protected static final List<String> DB_TYPE_KEY = new ArrayList<>();
	// 默认的函数映射实现
	// TODO
//	private static final Map<String, String> DEFAULT_FUNCTIONHANDLE = new HashMap<>();
	
	/**
	 * 设置pageHandle的初始化值
	 */
	static {
		DEFAULT_PAGEHANDLE.put("mysql", "com.integration.mybatis.impl.DefaultMysqlPageHandle");
		DEFAULT_PAGEHANDLE.put("dm6", "com.integration.mybatis.impl.DefaultDm6PageHandle");
	}
	
	/**
	 * 设置sqlHandle的初始化值
	 */
	static {
		SqlHandleEntity sqlHandle = new SqlHandleEntity("pageHandle", "com.integration.mybatis.interfaces.PageHandle", DEFAULT_PAGEHANDLE.get("mysql"), DEFAULT_PAGEHANDLE);
		DEFAULT_SQLHANDLE.add(sqlHandle);
	}
	
	/**
	 * 设置映射
	 */
	static {
		DB_PARSER_MAPPING.put("dm6", "dm");
		DB_PARSER_MAPPING.put("dm7", "dm");
	}
	
	/**
	 * 数据库的关键字
	 */
	static {
		DB_TYPE_KEY.add("hsqldb");
		DB_TYPE_KEY.add("h2");
		DB_TYPE_KEY.add("postgresql");
		DB_TYPE_KEY.add("phoenix");
		DB_TYPE_KEY.add("mysql");
		DB_TYPE_KEY.add("mariadb");
		DB_TYPE_KEY.add("sqlite");
		DB_TYPE_KEY.add("oracle");
		DB_TYPE_KEY.add("db2");
		DB_TYPE_KEY.add("informix");
		DB_TYPE_KEY.add("informix-sqli");
		DB_TYPE_KEY.add("sqlserver-sqli");
		DB_TYPE_KEY.add("sqlserver2012");
		DB_TYPE_KEY.add("derby");
		DB_TYPE_KEY.add("dm");
		DB_TYPE_KEY.add("edb");
	}
	
	/**
	 * 获取pageHandle的默认实现
	 * 
	 * @param dbType
	 * @return
	 */
	public static String getPageHandle(String dbType) {
		return DEFAULT_PAGEHANDLE.get(dbType);
	}
	
	/**
	 * 根据handleName获取实现的对象
	 * 
	 * @param handleName
	 * @return
	 */
	public static SqlHandleEntity getHandle(String handleName) {
		return DEFAULT_SQLHANDLE.stream().filter(e -> e.getAlias().equals(handleName)).findFirst().get();
	}
	
	/**
	 * 获取默认的handles
	 * 
	 * @return
	 */
	public static List<String> getHandles() {
		return DEFAULT_SQLHANDLE.stream().map(e -> e.getAlias()).collect(Collectors.toList());
	}
	
	/**
	 * 获取默认的达梦数据库实现
	 * 
	 * @return
	 */
	public static String getDmType() {
		return DEFAULT_DMTYPE;
	}
	
	/**
	 * 获取默认的sqlClient
	 * 
	 * @return
	 */
	public static String getSqlClient() {
		return DEFAULT_CLIENT;
	}
	
	/**
	 * 获取数据库的parser映射
	 * 
	 * @param dbType
	 * @return
	 */
	public static String getDbParserMapping(String dbType) {
		return Optional.ofNullable(DB_PARSER_MAPPING.get(dbType)).orElse(dbType);
	}
	
	/**
	 * 获取默认的数据源类型
	 * 
	 * @return
	 */
	public static String getDatasourceType() {
		return DATASOURCE_TYPE;
	}
	
	/**
	 * 获取数据库关键字
	 * 
	 * @return
	 */
	public static List<String> getDbTypeKey() {
		return DB_TYPE_KEY;
	}
}
