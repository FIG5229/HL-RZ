package com.integration.mybatis.factory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.integration.entity.SqlHandleEntity;
import com.integration.exceptions.SqlHandleException;
import com.integration.mybatis.DefaultDialectImpl;
import com.integration.mybatis.interfaces.FunctionHandle;
import com.integration.mybatis.interfaces.PageHandle;
import com.integration.mybatis.interfaces.SqlHandle;
import com.integration.mybatis.utils.MybatisConfigUtil;
import com.integration.utils.BeanUtils;
import com.integration.utils.MapUtils;

/**
 * sql处理的工厂
 * 
 * @author dell
 *
 */
public class SqlHandleFactory {
	
	private static Map<String, SqlHandle> sqlHandleM = new ConcurrentHashMap<>();
	
	/**
	 * 根据不同的数据库创建sqlHandle对象
	 * 
	 * @param dbType 数据库类型
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static SqlHandle createSqlHandle(String handleName, String dbType) {
		/*
		 * 在配置文件中进行搜索，看是否有新的配置
		 */
		SqlHandleEntity entity = MybatisConfigUtil.getImplByHandle(handleName);
		
		// 将接口fullname转为class
		Class<?> interCla = null;
		try {
			interCla = Class.forName(entity.getFullname());
		} catch (ClassNotFoundException e) {
			throw new SqlHandleException("接口类找不到："+ entity.getFullname());
		}
		
		if(!BeanUtils.isImplForInterface(interCla, SqlHandle.class)) {
			throw new SqlHandleException("创建"+ handleName +"对象失败。原因："+ handleName +"没有继承SqlHandle接口");
		}
		
		// 获取数据库的实现
		String implStr = Optional.ofNullable(entity.getDbType().get(dbType)).orElse(entity.getDef());
		if(StringUtils.isBlank(implStr)) {
			throw new SqlHandleException("创建"+ handleName +"对象失败。原因：未找到对应的实现");
		}
		
		/*
		 * 转换为class并进行接口的验证
		 */
		Class<?> impl = null;
		try {
			impl = Class.forName(implStr);
		} catch (ClassNotFoundException e) {
			throw new SqlHandleException("创建"+ handleName +"对象失败。原因：ClassNotFound");
		}
		
		if(!BeanUtils.isImplForInterface(impl, interCla)) {
			throw new SqlHandleException("创建"+ handleName +"对象失败。原因：对应的实现未实现"+ handleName +"接口");
		}
		
		try {
			return (SqlHandle) impl.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new SqlHandleException("创建"+ handleName +"对象失败。原因：对应的实现创建时失败");
		}
	}
	
	/**
	 * 获取sqlHandle
	 * 
	 * @param handleName
	 * @param dbType
	 * @return
	 */
	public static SqlHandle getSqlHandle(String handleName, String dbType) {
		String key = handleName +"_"+ dbType;
		if(!sqlHandleM.containsKey(key)) {
			SqlHandle sqlHandle = createSqlHandle(handleName, dbType);
			sqlHandleM.put(key, sqlHandle);
		}
		
		return sqlHandleM.get(key);
	}
	
	/**
	 * 根据不同的数据库functionHandle对象
	 * 
	 * @param dbType
	 * @return
	 */
	public static FunctionHandle createFunctionHandle(String dbType) {
		Map<String, String> functionHandleM = MybatisConfigUtil.getDbType("functionHandle");
		
		String impl = MapUtils.getStringValue(functionHandleM, dbType, DefaultDialectImpl.getPageHandle(dbType));
		if(StringUtils.isBlank(impl)) {
			throw new SqlHandleException("创建functionHandle对象失败。原因：未找到对应的实现");
		}
		
		Class<?> functionHandle = null;
		try {
			functionHandle = Class.forName(impl);
		} catch (ClassNotFoundException e) {
			throw new SqlHandleException("创建functionHandle对象失败。原因：ClassNotFound");
		}
		
		if(!BeanUtils.isImplForInterface(functionHandle, FunctionHandle.class)) {
			throw new SqlHandleException("创建functionHandle对象失败。原因：对应的实现未实现functionHandle接口");
		}
		
		try {
			return (FunctionHandle) functionHandle.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new SqlHandleException("创建functionHandle对象失败。原因：对应的实现创建时失败");
		}
	}
	
	/**
	 * 根据不同的数据库pageHandle对象
	 * 
	 * @param dbType
	 * @return
	 */
	public static PageHandle createPageHandle(String dbType) {
		Map<String, String> pageHandleM = MybatisConfigUtil.getDbType("pageHandle");
		
		String impl = MapUtils.getStringValue(pageHandleM, dbType, DefaultDialectImpl.getPageHandle(dbType));
		if(StringUtils.isBlank(impl)) {
			throw new SqlHandleException("创建pageHandle对象失败。原因：未找到对应的实现");
		}
		
		Class<?> pageHandle = null;
		try {
			pageHandle = Class.forName(impl);
		} catch (ClassNotFoundException e) {
			throw new SqlHandleException("创建pageHandle对象失败。原因：ClassNotFound");
		}
		
		if(!BeanUtils.isImplForInterface(pageHandle, PageHandle.class)) {
			throw new SqlHandleException("创建pageHandle对象失败。原因：对应的实现未实现pageHandle接口");
		}
		
		try {
			return (PageHandle) pageHandle.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new SqlHandleException("创建pageHandle对象失败。原因：对应的实现创建时失败");
		}
	}
}
