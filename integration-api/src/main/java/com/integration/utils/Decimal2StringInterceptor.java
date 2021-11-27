package com.integration.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * 数据库操作性能拦截器,记录耗时
 */
@Intercepts(value = {
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }) })
public class Decimal2StringInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object res;
		res = invocation.proceed();
		if (res instanceof List) {
			List list = (List) res;
			for (Object o : list) {
				o = changeMap(o);
			}
		} else {
			res = changeMap(res);
		}
		return res;
	}

	private Object changeMap(Object res) {
		if (res instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) res;
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() != null && entry.getValue() instanceof BigDecimal) {
					map.put(entry.getKey(), entry.getValue().toString());
				}
			}
			return map;
		}
		return res;
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
	}
}
