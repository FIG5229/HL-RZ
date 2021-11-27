package com.integration.feign;

import java.util.Map;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
/**
* @Package: com.integration.feign
* @ClassName: CzryServiceFallbackFactory
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
@Component
public class CzryServiceFallbackFactory implements FallbackFactory<CzryService>{


	@Override
	public CzryService create(Throwable arg0) {
		return new CzryService(){

			@Override
			public Object findCzryByIdFeign(String id) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map findCzryById(String id) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}


}
