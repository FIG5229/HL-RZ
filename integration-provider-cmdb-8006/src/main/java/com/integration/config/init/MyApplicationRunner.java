package com.integration.config.init;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.integration.mybatis.utils.dbInit.DbInitialization;
import com.integration.service.CiKpiService;
import com.integration.service.InfoService;
/**
* @Package: com.integration.config.init
* @ClassName: MyApplicationRunner
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 启动类
*/
@Component
@Order(1)
public class MyApplicationRunner implements ApplicationRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyApplicationRunner.class);
	@Resource
	DbInitialization dbInit;
	@Resource
	private CiKpiService ciKpiService;
	@Resource
	private InfoService infoService;
	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		try {
			// 初始化数据库
			dbInit.initialize();
			//初始化匹配iom_ci_kpi_type表与redis数据同步
			ciKpiService.initializeCiKpiType();
			//初始化指标数据
			ciKpiService.initializeCiKpiInfoToRedis();
			//初始化CI数据
			infoService.initializeCiInfoToRedis();
		} catch (Exception e) {
			LOGGER.error("======================dbInit fail======================");
			e.printStackTrace();
		}
	}

}