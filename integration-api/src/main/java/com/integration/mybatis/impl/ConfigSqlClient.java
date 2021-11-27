package com.integration.mybatis.impl;

import java.util.List;

import com.integration.mybatis.base.AbstractSqlClient;
import com.integration.mybatis.factory.SqlHandleFactory;
import com.integration.mybatis.interfaces.SqlHandle;
import com.integration.mybatis.utils.MybatisConfigUtil;

public class ConfigSqlClient extends AbstractSqlClient {

	/**
	 * 首先查看配置文件中是否对责任链进行了新的定义
	 * 如果没有则加载默认的责任链
	 */
	@Override
	public boolean createSqlHandles(String dbType) {
		List<String> handles = MybatisConfigUtil.getHandles();
		
		handles.stream().forEach(h -> {
			SqlHandle sqlHandle = SqlHandleFactory.getSqlHandle(h, dbType);
			appendSqlHandles(sqlHandle);
		});
		
		return true;
	}
}
