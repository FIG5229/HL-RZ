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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integration.service.impl.TypeItemServiceImpl;
 
/**
* @Package: com.integration.utils
* @ClassName: Decimal2StringInterceptor
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 数据库操作性能拦截器,记录耗时
*/
@Intercepts(value = {
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }),
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class }) })
public class Decimal2StringInterceptor implements Interceptor{
	private static final Logger logger = LoggerFactory.getLogger(Decimal2StringInterceptor.class);
    @Override
    public Object intercept(Invocation invocation) throws Throwable
    {
    	Object res;
    	res = invocation.proceed();
    	try {
    		if (res instanceof List) {
    			List list = (List) res;
    			for (Object o : list) {
    				o = changeMap(o);
    			}
    		}else {
    			res = changeMap(res);
    		}
		} catch (Exception e) {
			//BigDecimal --> String	失败
			logger.error(e.getMessage());
		}
        return res;
    }
    
    private Object changeMap(Object res) {
    	if (res instanceof Map) {
    		Map<String,Object> map = (Map<String,Object>) res;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
            	if (entry.getValue()!=null && entry.getValue() instanceof BigDecimal) {
                	map.put(entry.getKey(), entry.getValue().toString());
				}
            }
            return map;
		}
    	return res;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}
}
