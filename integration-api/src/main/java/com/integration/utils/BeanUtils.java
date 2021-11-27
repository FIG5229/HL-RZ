package com.integration.utils;

import java.util.stream.Stream;

/**
 * java bean工具类
 * 
 * @author suozhaoyu
 * @version 0.0.1
 * @since 0.0.1
 */
public class BeanUtils {

	/**
	 * 判断类是否是实现了某个一个接口
	 * 
	 * @param impl
	 * @param inter
	 * @return
	 */
	public static boolean isImplForInterface(Class<?> impl, Class<?> inter) {
		if(!inter.isInterface()) {
			throw new RuntimeException("要验证的不是一个借口");
		}
		
		if(inter.isAssignableFrom(impl)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 判断实现类是不是实现了其中的一个接口
	 * 
	 * @param impl
	 * @param inters
	 * @return
	 */
	public static boolean isImplForInterfaceOr(Class<?> impl, Class<?>... inters) {
		return Stream.of(inters).anyMatch(inter -> isImplForInterface(impl, inter));
	}
	
	/**
	 * 判断实现类是不是实现了全部的接口
	 * 
	 * @param impl
	 * @param inters
	 * @return
	 */
	public static boolean isImplForInterfaceAnd(Class<?> impl, Class<?>... inters) {
		return Stream.of(inters).allMatch(inter -> isImplForInterface(impl, inter));
	}
}
