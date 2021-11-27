package com.integration.fegin;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
/**
* @Package: com.integration.fegin
* @ClassName: OcpServiceFallbackFactory
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 智能运维feign调用
*/
@Component
public class OcpServiceFallbackFactory implements FallbackFactory<OcpService>{

	@Override
	public OcpService create(Throwable cause) {
		return new OcpService() {

			@Override
			public void jobTiming() {

			}
		};
	}
}
