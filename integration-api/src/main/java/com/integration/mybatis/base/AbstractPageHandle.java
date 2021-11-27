package com.integration.mybatis.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.MetaObject;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.parser.SQLExprParser;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.integration.exceptions.PageSqlException;
import com.integration.exceptions.SqlHandleException;
import com.integration.mybatis.DefaultDialectImpl;
import com.integration.mybatis.entity.Page;
import com.integration.mybatis.interfaces.PageHandle;

/**
 * 抽象的分页处理
 * 
 * @author dell
 *
 */
public abstract class AbstractPageHandle implements PageHandle {
	
	@Override
	public String sqlHandle(String dbType, MetaObject metaObject, Connection conn) {
		/*
		 * 获取sql和参数
		 */
		String sql = (String) metaObject.getValue("delegate.boundSql.sql");
		String countSql = getCountSql(sql, dbType);
		ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");
		
		Page page = getPage(parameterHandler);
		if(null == page) {
			throw new SqlHandleException("获取分页数据错误");
		}
        
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        List<ParameterMapping> pL = boundSql.getParameterMappings();
        Iterator<ParameterMapping> pi = pL.iterator();
        while (pi.hasNext()) {
			ParameterMapping p = pi.next();
			if("pageIndex".equals(p.getProperty()) || "pageCount".equals(p.getProperty())) {
				pi.remove();
			}
		}

		try {
			PreparedStatement countStatement = conn.prepareStatement(countSql);
	        parameterHandler.setParameters(countStatement);
	        ResultSet rs = countStatement.executeQuery();
	        if (rs.next()) {
	            page.setTotalResult(rs.getInt(1));
	        }
		} catch (SQLException e) {
			throw new SqlHandleException("获取总记录数失败");
		}
		
		return getPageSql(sql, dbType, page);
	}
	
	/**
	 * 获取分页数据
	 * 
	 * @param parameterHandler
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Page getPage(ParameterHandler parameterHandler) {
		Object paraObject = parameterHandler.getParameterObject();
		
		Page page = null;
		if(paraObject instanceof Map) {
			Map<String, Object> paraM = (Map<String, Object>) paraObject;
			if(paraM.containsKey("page")) {
				page = (Page) paraM.get("page");
			}
			
			if(page == null) {
	        	int pageIndex = MapUtils.getIntValue(paraM, "pageIndex");
	        	int pageCount = MapUtils.getIntValue(paraM, "pageCount");
	        	
	        	page = new Page(pageIndex, pageCount);
	        }
		} else {
			Method method;
			try {
				method = paraObject.getClass().getMethod("getPage");
				Object value = method.invoke(paraObject);
				page = (Page) value;
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				return null;
			}
		}
		
        return page;
	}

	/**
	 *  获取count的sql语句
	 */
	@Override
	public String getCountSql(String sql, String dbType) {
		SQLSelectStatement stmt = getSqlSelectStatement(sql, dbType);
		return getCountSqlOnSelect((SQLSelectStatement) stmt, dbType);
	}
	
	/**
	 * 获取SQLSelectStatement对象
	 * 
	 * @param sql
	 * @param dbType
	 * @return
	 */
	protected SQLSelectStatement getSqlSelectStatement(String sql, String dbType) {
		List<SQLStatement> stmlList = SQLUtils.parseStatements(sql, DefaultDialectImpl.getDbParserMapping(dbType));
		SQLStatement stmt = stmlList.get(0);
		if(stmt instanceof SQLSelectStatement) {
			return (SQLSelectStatement) stmt;
		} else {
			throw new PageSqlException("分页sql解析错误");
		}
	}

	/**
	 * 针对select
	 * 
	 * @param selectState
	 * @return
	 */
	protected String getCountSqlOnSelect(SQLSelectStatement selectState, String dbType) {
		SQLSelect sqlSelect = selectState.getSelect();
		SQLSelectQueryBlock query = (SQLSelectQueryBlock) sqlSelect.getQuery();
		
		/*
		 * 将order by 去除掉
		 */
		removeOrderBy(query);
		
		/*
		 * 把分页信息去除掉
		 */
		removePageInfo(query);
		
		/*
		 * 组装count信息
		 */
		if(query.getDistionOption() == 0) {
			// 主查询中不含有distinct
			return intoNormalCount(sqlSelect, dbType);
		}
		
		return intoDistictNormalCount(sqlSelect, dbType);
	}

	/**
	 * 去除order by
	 * 
	 * @param query
	 */
	protected void removeOrderBy(SQLSelectQueryBlock query) {
		SQLOrderBy orderby = query.getOrderBy();
		if(null == orderby) {
			return;
		}
		List<SQLSelectOrderByItem> orderbyItemL = orderby.getItems();
		if(orderbyItemL == null || orderbyItemL.isEmpty()) {
			return;
		}
		orderbyItemL.clear();
	}
	
	/**
	 * 处理sql中存在的分页信息
	 * 
	 * @param query
	 */
	protected abstract void removePageInfo(SQLSelectQueryBlock query); 
	
	/**
	 * 往不含有distinct的sql中放入count
	 * 
	 * @param query
	 */
	protected String intoNormalCount(SQLSelect sqlSelect, String dbType) {
		SQLSelectQueryBlock query = (SQLSelectQueryBlock) sqlSelect.getQuery();
		List<SQLSelectItem> itemL = query.getSelectList();
		itemL.clear();
		
		SQLExprParser exprParser = SQLParserUtils.createExprParser("count(*)", dbType);
		SQLExpr sqlExpr = exprParser.expr();
		
		SQLSelectItem sqlSelectItem = new SQLSelectItem();
		sqlSelectItem.setExpr(sqlExpr);
		
		query.addSelectItem(sqlSelectItem);
		
		return sqlSelect.toString();
	}
	
	/**
	 * 往含有distinct的sql中放入count
	 * 
	 * @param sqlSelect
	 * @param dbType
	 * @return
	 */
	protected String intoDistictNormalCount(SQLSelect sqlSelect, String dbType) {
		StringBuilder sqlSb = new StringBuilder("select count(*) from (")
				.append(sqlSelect.toString())
				.append(") d_a");
		return sqlSb.toString();
	}

	@Override
	public boolean handleRule(MappedStatement mappedState) {
		String mapId = mappedState.getId();
		SqlCommandType commandType = mappedState.getSqlCommandType();
		
		if(commandType == SqlCommandType.SELECT && mapId.matches(".+Page$")) {
			return true;
		}
		
		return false;
	}
}
