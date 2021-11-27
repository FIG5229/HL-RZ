package com.integration.mybatis.base;

import java.sql.Connection;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import com.integration.mybatis.factory.SqlClientFactory;
import com.integration.mybatis.interfaces.SqlClient;
import com.integration.mybatis.utils.MybatisConfigUtil;

/**
 * 针对mybatis预处理的sql进行处理的父类
 * 
 * @author dell
 *
 */
public abstract class AbstractInterceptor implements Interceptor {
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Connection conn = (Connection) invocation.getArgs()[0];
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject mateObjectHanlder = SystemMetaObject.forObject(statementHandler);
		
		// 获取查询接口映射的相关信息
		String dbType = MybatisConfigUtil.getDbType(conn);
		SqlClient client = SqlClientFactory.getSqlClent();
		String sql = client.doHandle(dbType, mateObjectHanlder, conn);
		if(StringUtils.isBlank(sql)) {
			sql = (String) mateObjectHanlder.getValue("delegate.boundSql.sql");
		}
		
		mateObjectHanlder.setValue("delegate.boundSql.sql", sql);
		
		return invocation.proceed();
	}
	
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}
}
