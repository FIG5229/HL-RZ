package com.integration.aop.log.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作记录注解
 * 
 * @author dell
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OptionLog {

	/**
	 * 所属模块
	 * 
	 * @return
	 */
	String module();
	
	/**
	 * 功能描述
	 * 
	 * @return
	 */
	String desc();
	
	/**
	 * 记录入参
	 * 
	 * @return
	 */
	boolean writeParam() default false;
	
	/**
	 * 记录出参
	 * 
	 * @return
	 */
	boolean writeResult() default false;
}
