package com.integration.feign;


import com.integration.service.impl.CiInitializationServiceImpl;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.feign
* @ClassName: EmvEvCurrentServiceFallbackFactory
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 事件可视化feign调用
*/
@Component
public class EmvEvCurrentServiceFallbackFactory implements
		FallbackFactory<EmvEvCurrentService> {
	private final static Logger logger = LoggerFactory.getLogger(EmvEvCurrentServiceFallbackFactory.class);
	@Override
	public EmvEvCurrentService create(Throwable cause) {
		logger.error("根据CIID查询告警数据时异常", cause);
		return new EmvEvCurrentService() {

			@Override
			public List<Map<String, Object>> selectCountByCiIdList(String[] ciIdList,String[] ciCodeList) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<String, Object> findEventByCiIDs(String ciId) {
				// TODO Auto-generated method stub
				return null;
			}


		};
	}

}
