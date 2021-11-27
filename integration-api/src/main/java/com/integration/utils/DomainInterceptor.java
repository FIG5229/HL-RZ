package com.integration.utils;

import com.integration.aop.log.annotations.InterceptAnnotation;
import com.integration.utils.token.TokenUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Properties;

/**
 * @ProjectName: integration
 * @Package: com.integration.utils
 * @ClassName: DomainInterceptor
 * @Author: ztl
 * @Date: 2020-06-02
 * @Version: 1.0
 * @description:数据域拦截器，动态修改原有SQL，添加数据域限制条件
 */
@SuppressWarnings("rawtypes")
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DomainInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler= (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        //先拦截到RoutingStatementHandler，里面有个StatementHandler类型的delegate变量，其实现类是BaseStatementHandler，然后就到BaseStatementHandler的成员变量mappedStatement
          MappedStatement mappedStatement = null;
        try {
            //获取MapperStatement对象，获取到sql的详细信息
            Object realTarget = realTarget(invocation.getTarget());
            //获取metaObject对象
            MetaObject metaObjects = SystemMetaObject.forObject(realTarget);
            //获取MappedStatement对象
            mappedStatement = (MappedStatement) metaObjects.getValue("delegate.mappedStatement");
        }catch (Exception e){
            e.printStackTrace();

        }
        if (mappedStatement != null){
            //获取到原始sql语句
            String sql = boundSql.getSql();
            String mSql = sql;
            //注解逻辑判断  添加注解了才拦截
            Class<?> classType = Class.forName(mappedStatement.getId().substring(0, mappedStatement.getId().lastIndexOf(".")));
            String mName = mappedStatement.getId().substring(mappedStatement.getId().lastIndexOf(".") + 1, mappedStatement.getId().length());
            for (Method method : classType.getDeclaredMethods()) {
                if (method.isAnnotationPresent(InterceptAnnotation.class) && mName.equals(method.getName())) {
                    //获取自定义注解
                    InterceptAnnotation interceptorAnnotation = method.getAnnotation(InterceptAnnotation.class);
                    //获取数据域ID
                    String domainId = TokenUtils.getTokenDomainId();
                    //判断是否需要添加数据域限制条件
                    if (interceptorAnnotation.flag()) {
                        //判断数据域是否为null
                        if (domainId != null){
                            //判断SQL语句中是否包含order by条件
                            if (sql.contains("order")){
                                mSql = sql.split("order")[0] + " AND DOMAIN_ID = "+domainId + " order "+sql.split("order")[1];
                            }else{
                                mSql = sql + " AND DOMAIN_ID = "+domainId;
                            }
                        }else {
                            mSql = sql;
                        }

                    }
                }
            }
            //通过反射修改sql语句
            Field field = boundSql.getClass().getDeclaredField("sql");
            field.setAccessible(true);
            field.set(boundSql, mSql);
        }

        return invocation.proceed();
    }


    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
    /**
     * <p>
     * 获得真正的处理对象,可能多层代理.
     * </p>
     */
    @SuppressWarnings("unchecked")
    public static <T> T realTarget(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return realTarget(metaObject.getValue("h.target"));
        }
        return (T) target;
    }
}
