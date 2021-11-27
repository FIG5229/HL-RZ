package com.integration.mybatis.impl;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;

import com.integration.mybatis.base.AbstractInterceptor;

/**
 * 默认mybatis拦截器实现
 * 
 * @author dell
 *
 */
@Intercepts({
	@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class DefaultInterceptor extends AbstractInterceptor {

	@Override
	public void setProperties(Properties properties) {

	}
}
