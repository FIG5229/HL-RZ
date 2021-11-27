package com.integration.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * aop工具
 * 
 * @author suozhaoyu
 * @version 0.0.1
 */
public class SpringAopUtil {

	/**
	 * 获取调用方法
	 * 
	 * @param joinPoint
	 * @return
	 */
	public static Method getMethod(JoinPoint joinPoint){
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		return methodSignature.getMethod();
	}
	
	/**
	 * 获取方法上的注解对象
	 * 
	 * @param joinPoint
	 * @param annotationType
	 * @return
	 */
	public static <A extends Annotation> A getAnnotationInMethod(JoinPoint joinPoint, Class<A> annotationType){
		Method method = getMethod(joinPoint);
		return getAnnotationInMethod(method, annotationType);
	}
	
	/**
	 * 获取方法上的注解对象
	 * 
	 * @param method
	 * @param annotationType
	 * @return
	 */
	public static <A extends Annotation> A getAnnotationInMethod(Method method, Class<A> annotationType){
		return AnnotationUtils.getAnnotation(method, annotationType);
	}
}
