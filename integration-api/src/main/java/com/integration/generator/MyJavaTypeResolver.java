package com.integration.generator;

import java.sql.Types;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

public class MyJavaTypeResolver extends JavaTypeResolverDefaultImpl {

	public MyJavaTypeResolver() {
		super();
		super.typeMap.put(Types.BIGINT, new JavaTypeResolverDefaultImpl.JdbcTypeInformation("BIGINT",
				new FullyQualifiedJavaType(String.class.getName())));
		
		super.typeMap.put(Types.DECIMAL, new JavaTypeResolverDefaultImpl.JdbcTypeInformation("DECIMAL",
				new FullyQualifiedJavaType(String.class.getName())));
		
		super.typeMap.put(Types.LONGVARCHAR, new JavaTypeResolverDefaultImpl.JdbcTypeInformation("LONGVARCHAR",
				new FullyQualifiedJavaType(String.class.getName())));
		
		super.typeMap.put(Types.LONGVARCHAR, new JavaTypeResolverDefaultImpl.JdbcTypeInformation("VARCHAR",
				new FullyQualifiedJavaType(String.class.getName())));
	}
}
