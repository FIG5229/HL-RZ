package com.integration.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class CzryServiceFallbackFactory implements FallbackFactory<CzryService>{


	@Override
	public CzryService create(Throwable arg0) {
		return () -> null;
	}


}
