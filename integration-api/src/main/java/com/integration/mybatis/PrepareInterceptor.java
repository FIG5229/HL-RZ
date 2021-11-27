package com.integration.mybatis;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;

import com.integration.mybatis.base.AbstractInterceptor;

/**
 * 对mybatis预处理sql进行操作
 * 
 * @author dell
 *
 */
@Intercepts({
	@Signature(type=StatementHandler.class, method="prepare", args={Connection.class, Integer.class})
})
public class PrepareInterceptor extends AbstractInterceptor {

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}
}
