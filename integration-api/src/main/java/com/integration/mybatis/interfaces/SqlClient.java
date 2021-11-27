package com.integration.mybatis.interfaces;

import java.sql.Connection;

import org.apache.ibatis.reflection.MetaObject;

/**
 * sql处理责任接口
 * 
 * @author dell
 *
 */
public interface SqlClient {

	/**
	 * 创建责任链
	 * 
	 * @return
	 */
	boolean createSqlHandles(String dbType);
	
	/**
	 * 追加责任链
	 * 
	 * @param sqlHandle
	 * @return
	 */
	boolean appendSqlHandles(SqlHandle sqlHandle);
	
	/**
	 * 责任链处理
	 * 
	 * @return
	 */
	String doHandle(String dbType, MetaObject metaObject, Connection conn);
}
