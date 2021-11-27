package com.integration.mybatis.impl;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLLimit;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.parser.SQLExprParser;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.util.JdbcConstants;
import com.integration.mybatis.entity.Page;

/**
 * 达梦6分页处理
 * 
 * @author dell
 *
 */
public class DefaultDm6PageHandle extends DefaultMysqlPageHandle{

	@Override
	public void setLimit(SQLSelectQueryBlock query, Page page) {
		String dbType = JdbcConstants.MYSQL;
		SQLExprParser limitOffsetParser  = SQLParserUtils.createExprParser(page.getPageSize() +"", dbType);
		SQLExpr sqlLimitOffsetExpr = limitOffsetParser.expr();
		SQLExprParser limitRowCountParser  = SQLParserUtils.createExprParser(page.getOffset() +"", dbType);
		SQLExpr sqlLimitRowCountExpr = limitRowCountParser.expr();
		SQLLimit sqlLimit = new SQLLimit();
		sqlLimit.setOffset(sqlLimitOffsetExpr);
		sqlLimit.setRowCount(sqlLimitRowCountExpr);
		query.setLimit(sqlLimit);
	}
}
