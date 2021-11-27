package com.integration.config.feign;

import com.alibaba.fastjson.JSONObject;
import com.integration.entity.PageResult;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class MyErrorDecoder implements ErrorDecoder {

	Logger log = LoggerFactory.getLogger(MyErrorDecoder.class);

	@Override
	public Exception decode(String methodKey, Response response) {
		String msg = String.format("服务间间调动错误，请联系管理员！%s",methodKey);
		try {
			String message = Util.toString(response.body().asReader());
			log.error("feign调用异常：");
			log.error(message);
			return new CustomFeignException(msg, message);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new CustomFeignException(msg, msg);

	}


}
