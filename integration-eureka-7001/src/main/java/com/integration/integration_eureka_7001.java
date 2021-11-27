package com.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer   	//eureka服务端注解
public class integration_eureka_7001 {

	public static void main(String[] args) {
		SpringApplication.run(integration_eureka_7001.class, args);

	}

}
