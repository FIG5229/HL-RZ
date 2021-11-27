package com.integration.mybatis.interfaces;

import java.sql.Connection;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;

/**
 * sql过滤处理接口
 * 
 * @author dell
 *
 */
public interface SqlHandle {

	/**
	 * 处理sql
	 * 
	 * @param sql
	 * @param dbType
	 * @param params
	 * @return
	 */
	String sqlHandle(String dbType, MetaObject metaObject, Connection conn);
	
	/**
	 * 验证处理规则，通过继续处理
	 * 
	 * @param mappedState
	 * @return
	 */
	boolean handleRule(MappedStatement mappedState);
}
