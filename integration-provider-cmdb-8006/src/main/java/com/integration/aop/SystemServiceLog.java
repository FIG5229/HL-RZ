package com.integration.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
* @Package: com.integration.aop
* @ClassName: SystemServiceLog
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
public @interface SystemServiceLog {
	/**
	 * 定义成员
	 */
	String decription() default "";

}
