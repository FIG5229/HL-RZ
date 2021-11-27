package com.integration.mybatis.base;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;

import com.integration.mybatis.interfaces.SqlClient;
import com.integration.mybatis.interfaces.SqlHandle;

/**
 * 抽象的sql责任处理
 * 
 * @author dell
 *
 */
public abstract class AbstractSqlClient implements SqlClient {

	protected List<SqlHandle> sqlHandleL = new ArrayList<>();
	
	@Override
	public synchronized boolean appendSqlHandles(SqlHandle sqlHandle) {
		return sqlHandleL.add(sqlHandle);
	}

	@Override
	public String doHandle(String dbType, MetaObject metaObject, Connection conn) {
		MappedStatement mappedState = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

		if(sqlHandleL.isEmpty()) {
			createSqlHandles(dbType);
		}
		CopyOnWriteArrayList<SqlHandle> sqlHandleLCopy = new CopyOnWriteArrayList<>(sqlHandleL.toArray(new SqlHandle[0]));
		String result = null;
		for(SqlHandle sqlHandle : sqlHandleLCopy) {
			if(!sqlHandle.handleRule(mappedState)){
				continue;
			}
			result = sqlHandle.sqlHandle(dbType, metaObject, conn);
		}
		
		return result;
	}
}
