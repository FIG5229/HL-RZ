package com.integration.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring bean的工具类
 * 
 * @author suozhaoyu
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class SpringBeanUtils implements ApplicationContextAware{

	private static ApplicationContext ac;
	
	/**
	 * 通过bean名称获取bean
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBeanByName(String beanName) {
		return ac.getBean(beanName);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ac = applicationContext;
	}
}
