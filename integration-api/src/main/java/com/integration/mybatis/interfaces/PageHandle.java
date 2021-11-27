package com.integration.mybatis.interfaces;

import com.integration.mybatis.entity.Page;

/**
 * 针对分页进行sql处理的handle
 * 
 * @author dell
 *
 */
public interface PageHandle extends SqlHandle {

	/**
	 * 获取分页sql
	 * 
	 * @param sql
	 * @param dbType
	 * @return
	 */
	String getCountSql(String sql, String dbType);
	
	/**
	 * 获取分页sql
	 * 
	 * @param sql
	 * @param dbType
	 * @return
	 */
	String getPageSql(String sql, String dbType, Page page);
}
