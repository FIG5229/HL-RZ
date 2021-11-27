package com.integration;

import com.integration.config.IomPlatformParam;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Import(IomPlatformParam.class)
@SpringBootApplication
@EnableEurekaClient
//@EnableZuulProxy				//Zuul注解
//@EnableRedisHttpSession(redisFlushMode = RedisFlushMode.IMMEDIATE)
@MapperScan({ "com.integration.generator.dao", "com.integration.dao" })//session共享,每个模块都要添加
@EnableFeignClients(basePackages = { "com.integration" })
public class integration_zuul {	
	public static void main(String[] args) {
		SpringApplication.run(integration_zuul.class, args);

	}


	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter() {
			@Override
			public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
				return chain.filter(exchange);
			}
		};
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
