package com.integration;

import com.integration.entity.PageResult;
import com.integration.utils.DataUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@SpringBootApplication
@EnableEurekaClient
/**
 * 表示服务启动后会自动注册进eureka
 */
@EnableDiscoveryClient
/**
 * 向外暴露服务,让别人发现自己
 */
@EnableFeignClients(basePackages = { "com.integration" })
/**
 * Feign客户端注解 扫描的api下feignclient注解所在的包
 */
@ComponentScan("com.integration")
/**
 * 扫描包注解, 扫描的api下feignclient注解所在的包
 */
@MapperScan({ "com.integration.generator.dao", "com.integration.dao" })
/**
 * @Package: com.integration
 * @ClassName: integration_provider_8006
 * @Author: ztl
 * @Date: 2021-08-09
 * @Version: 1.0
 * @description: 启动类
 */
@RestController
public class integration_provider_8006 {

	
	public static void main(String[] args) {
		
		SpringApplication.run(integration_provider_8006.class, args);
		
		
	}
	

	    

	@RequestMapping("appStatus")
	public PageResult appStatus(){
		return DataUtils.returnPr(true);
	}
	

}
