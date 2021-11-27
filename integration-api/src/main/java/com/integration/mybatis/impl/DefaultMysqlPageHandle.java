package com.integration.mybatis.impl;

import org.apache.commons.lang.StringUtils;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLLimit;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.parser.SQLExprParser;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.util.JdbcConstants;
import com.integration.mybatis.base.AbstractPageHandle;
import com.integration.mybatis.entity.Page;

/**
 * 默认的mysql分页实现
 * 
 * @author dell
 *
 */
public class DefaultMysqlPageHandle extends AbstractPageHandle{

	@Override
	public String getPageSql(String sql, String dbType, Page page) {
		if(StringUtils.isBlank(dbType)) {
			dbType = JdbcConstants.MYSQL;
		}
		SQLSelectStatement stmt = getSqlSelectStatement(sql, dbType);
		SQLSelect sqlSelect = stmt.getSelect();
		SQLSelectQueryBlock query = (SQLSelectQueryBlock) sqlSelect.getQuery();
		
		setLimit(query, page);
		
		return sqlSelect.toString();
	}

	@Override
	protected void removePageInfo(SQLSelectQueryBlock query) {
		query.setLimit(null);
	}
	
	/**
	 * 进行limit处理
	 * 
	 * @param query
	 * @param pageIndex
	 * @param pageSize
	 */
	public void setLimit(SQLSelectQueryBlock query, Page page) {
		String dbType = JdbcConstants.MYSQL;
		SQLExprParser limitOffsetParser  = SQLParserUtils.createExprParser(page.getOffset() +"", dbType);
		SQLExpr sqlLimitOffsetExpr = limitOffsetParser.expr();
		SQLExprParser limitRowCountParser  = SQLParserUtils.createExprParser(page.getPageSize() +"", dbType);
		SQLExpr sqlLimitRowCountExpr = limitRowCountParser.expr();
		SQLLimit sqlLimit = new SQLLimit();
		sqlLimit.setOffset(sqlLimitOffsetExpr);
		sqlLimit.setRowCount(sqlLimitRowCountExpr);
		query.setLimit(sqlLimit);
	}
}
