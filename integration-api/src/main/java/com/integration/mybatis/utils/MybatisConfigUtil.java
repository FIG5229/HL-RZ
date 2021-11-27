package com.integration.mybatis.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;

import com.integration.config.MyBatisConfig;
import com.integration.entity.SqlHandleEntity;
import com.integration.exceptions.SqlHandleException;
import com.integration.mybatis.DefaultDialectImpl;
import com.integration.utils.SpringBeanUtils;

/**
 * mybatisConfig的工具类
 * 
 * @author dell
 *
 */
public class MybatisConfigUtil {

	private static final MyBatisConfig myBatisConfig = (MyBatisConfig) SpringBeanUtils.getBeanByName("myBatisConfig");
	
	private static String dbType;
	
	/**
	 * 获取实现的对象
	 * 
	 * @param handle
	 * @return
	 */
	public static SqlHandleEntity getImplByHandle(String handle) {
		List<SqlHandleEntity> entityL = myBatisConfig.getImpl();
		return Optional.ofNullable(entityL)
				.orElse(new ArrayList<>())
				.stream()
				.filter(e -> e.getAlias().equals(handle))
				.findFirst()
				.orElse(DefaultDialectImpl.getHandle(handle));
	}
	
	/**
	 * 获取对象中的dbType值
	 * 
	 * @param handle
	 * @return
	 */
	public static Map<String, String> getDbType(String handle) {
		return getImplByHandle(handle).getDbType();
	}
	
	/**
	 * 获取handles，，若未配置则取默认的
	 * 
	 * @return
	 */
	public static List<String> getHandles() {
		return Optional.ofNullable(myBatisConfig.getHandles()).orElse(DefaultDialectImpl.getHandles());
	}
	
	/**
	 * 获取sqlClient，若未配置则取默认的
	 * 
	 * @return
	 */
	public static String getSqlClient() {
		return Optional.ofNullable(myBatisConfig.getSqlClient()).orElse(DefaultDialectImpl.getSqlClient());
	}
	
	/**
	 * 获取配置的dmtype
	 * 
	 * @return
	 */
	public static String getDmType() {
		return Optional.ofNullable(myBatisConfig.getDmType()).orElse(DefaultDialectImpl.getDmType());
	}
	
	/**
	 * 获取默认的数据源类型
	 * 
	 * @return
	 */
	public static String getDatasourceType() {
		if(StringUtils.isBlank(myBatisConfig.getDatasourceType())) {
			return DefaultDialectImpl.getDatasourceType();
		}
		
		return "multi".equals(myBatisConfig.getDatasourceType()) ? "multi" : "single";
	}
	
	/**
	 * 获取dbType
	 * 
	 * @param mappedStatement
	 * @return
	 */
	public static String getDbType(Connection conn) {
		if(null == dbType) {
			String d = setDbType(conn);
			
			if("single".equals(getDatasourceType())) {
				dbType = d;
			} else {
				return d;
			}
		}
		
		return dbType;
	}
	
	private static String setDbType(Connection conn) {
		// 查看配置文件
		String dbType = null;
		if(StringUtils.isBlank(dbType)) {
			try {
				DatabaseMetaData metaData = conn.getMetaData();
				final String jdbcUrl = metaData.getURL();
				dbType = DefaultDialectImpl.getDbTypeKey().stream().filter(d -> jdbcUrl.indexOf(d) != -1).findAny().orElse("mysql");
			} catch (SQLException e) {
				throw new SqlHandleException("获取数据库链接数据失败");
			}
		}
		
		if("dm".equals(dbType)) {
			dbType = MybatisConfigUtil.getDmType();
		}
		
		return dbType;
	}
}
