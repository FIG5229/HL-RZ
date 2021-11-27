package com.integration.feign;

import java.util.List;

import com.integration.entity.Param;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
/**
* @Package: com.integration.feign
* @ClassName: DfileServiceFallbackFactory
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
@Component
public class DfileServiceFallbackFactory implements
		FallbackFactory<DfileService> {

	@Override
	public DfileService create(Throwable cause) {
		return new DfileService() {

			@Override
			public List getDfile(String lable) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List getResult(Param parameter) {
				// TODO Auto-generated method stub
				return null;
			}

		};
	}

}
