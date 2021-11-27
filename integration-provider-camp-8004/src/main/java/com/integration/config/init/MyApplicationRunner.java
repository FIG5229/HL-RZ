package com.integration.config.init;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.integration.mybatis.utils.dbInit.DbInitialization;
/**
* @Package: com.integration.config.init
* @ClassName: MyApplicationRunner
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 启动类
*/
@Component
@Order(1)
public class MyApplicationRunner implements ApplicationRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyApplicationRunner.class);
	@Resource
	DbInitialization dbInit;

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		try {
			// 初始化数据库
			dbInit.initialize();
		} catch (Exception e) {
			LOGGER.error("======================dbInit fail======================");
			e.printStackTrace();
		}

	}

}