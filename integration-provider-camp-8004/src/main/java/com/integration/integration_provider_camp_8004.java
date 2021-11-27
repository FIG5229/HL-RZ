package com.integration;


import com.integration.config.HttpClientUtils;
import com.integration.config.IomPlatformParam;
import com.integration.entity.PageResult;
import com.integration.utils.DataUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Import(IomPlatformParam.class)
@EnableScheduling
//排除自动配置可以开发时防止定时任务的干扰
//@SpringBootApplication(exclude = {QuartzAutoConfiguration.class})
@SpringBootApplication
/**
 * 表示服务启动后会自动注册进eureka
 */
@EnableEurekaClient
@MapperScan({ "com.integration.generator.dao", "com.integration.dao" })
@EnableFeignClients(basePackages = { "com.integration" })
/**
* @Package: com.integration
* @ClassName: integration_provider_camp_8004
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 启动类
*/
@RestController
public class integration_provider_camp_8004 {
	public static void main(String[] args) {
		SpringApplication.run(integration_provider_camp_8004.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate httpsRestTemplate(HttpComponentsClientHttpRequestFactory httpsFactory) {

		RestTemplate restTemplate = new RestTemplate(httpsFactory);

		restTemplate.setErrorHandler(

				new ResponseErrorHandler() {

					@Override

					public boolean hasError(ClientHttpResponse clientHttpResponse) {

						return false;

					}

					@Override

					public void handleError(ClientHttpResponse clientHttpResponse) {

					}

				});

		return restTemplate;

	}
	@Bean(name = "httpsFactory")

	public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory()

			throws Exception {

		CloseableHttpClient httpClient = HttpClientUtils.acceptsUntrustedCertsHttpClient();

		HttpComponentsClientHttpRequestFactory httpsFactory =

				new HttpComponentsClientHttpRequestFactory(httpClient);

		httpsFactory.setReadTimeout(40000);

		httpsFactory.setConnectTimeout(40000);

		return httpsFactory;

	}

	@RequestMapping("appStatus")
	public PageResult appStatus(){
		return DataUtils.returnPr(true);
	}

}
