package com.integration.aop.log.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ProjectName: integration
 * @Package: com.integration.aop.log.annotations
 * @ClassName: InterceptAnnotation
 * @Author: ztl
 * @Date: 2020-06-02
 * @Version: 1.0
 * @description:自定义注解（用于标定是否需要添加数据域权限限制）
 */
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface InterceptAnnotation {
    boolean flag() default  true;
}
