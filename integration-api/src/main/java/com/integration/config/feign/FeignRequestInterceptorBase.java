package com.integration.config.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.integration.utils.token.TokenUtils;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign请求拦截器
 * 
 * @author yinjihuan
 * @create 2017-11-10 17:25
 **/
@Configuration
public class FeignRequestInterceptorBase implements RequestInterceptor {
	@Override
	public void apply(RequestTemplate template) {
		template.header("token", TokenUtils.getToken());
	}
	@Bean
	public FeignHystrixConcurrencyStrategyIntellif feignHystrixConcurrencyStrategy() {
		return new FeignHystrixConcurrencyStrategyIntellif();
	}
}
