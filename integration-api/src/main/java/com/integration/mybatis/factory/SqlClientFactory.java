package com.integration.mybatis.factory;

import org.apache.commons.lang.StringUtils;

import com.integration.exceptions.SqlHandleException;
import com.integration.mybatis.interfaces.SqlClient;
import com.integration.mybatis.utils.MybatisConfigUtil;
import com.integration.utils.BeanUtils;

/**
 * 
 * 用户端选择
 * 
 * @author dell
 *
 */
public class SqlClientFactory {
	
	private static SqlClient sqlClient;

	/**
	 * 创建sqlClient实现
	 * 
	 * @return
	 */
	public static SqlClient createSqlClient() {
		String clientStr = MybatisConfigUtil.getSqlClient();
		
		if(StringUtils.isBlank(clientStr)) {
			throw new SqlHandleException("创建SqlClient对象失败。原因：未找到对应的实现");
		}
		
		Class<?> client = null;
		try {
			client = Class.forName(clientStr);
		} catch (ClassNotFoundException e) {
			throw new SqlHandleException("接口类找不到："+ clientStr);
		}
		
		if(!BeanUtils.isImplForInterface(client, SqlClient.class)) {
			throw new SqlHandleException("创建SqlClient对象失败。原因："+ clientStr +"没有继承SqlClient接口");
		}
		
		try {
			return (SqlClient) client.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new SqlHandleException("创建SqlClient对象失败。原因：对应的实现创建时失败");
		}
	}
	
	/**
	 * 获取sqlClient
	 * 
	 * @return
	 */
	public static SqlClient getSqlClent() {
		if(sqlClient == null) {
			synchronized(SqlClientFactory.class) {
				if(sqlClient == null) {
					sqlClient = createSqlClient();
				}
			}
		}
		
		return sqlClient;
	}
}
