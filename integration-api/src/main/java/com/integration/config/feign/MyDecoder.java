package com.integration.config.feign;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.integration.config.Advice.ErrorControllerAdviceBase;
import com.integration.config.Advice.MsgException;
import com.integration.entity.PageResult;
import com.integration.utils.gson.MapTypeAdapter;

import feign.Response;
import feign.Util;
import feign.codec.Decoder;

@Configuration
public class MyDecoder implements Decoder {

	@Override
	public Object decode(Response response, Type type) throws IOException {
		if (response.status() == 404) return Util.emptyValueOf(type);
		if (response.body() == null) return null;
		String resBody = IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8);
		if (StringUtils.isEmpty(resBody)) return null;
		if (String.class.equals(type)) return resBody;
		try {
			Object object = getGson().fromJson(resBody, type);
			return object;
		} catch (Exception exception) {
			String msg = "系统间调动错误，请联系管理员！";
			String exceptionMsg = ErrorControllerAdviceBase.getExceptionAllinformation(exception);
			try {
				//如果返回值是个错误提示
				JSONObject jsonObject = JSON.parseObject(resBody);
				if (PageResult.isPageResult(jsonObject)) {
					if (StringUtils.isNotEmpty(jsonObject.getString("returnMessage"))) {
						msg = jsonObject.getString("returnMessage");
					}
					exceptionMsg = jsonObject.getString("returnExceptionMsg");
				}
			} catch (Exception e) {
			}
			throw new MsgException(msg, exceptionMsg);
		}

	}
	
	public Gson getGson() {
        Gson gson = new GsonBuilder().create();
        try {
            Field factories = Gson.class.getDeclaredField("factories");
            factories.setAccessible(true);
            Object o = factories.get(gson);
            Class<?>[] declaredClasses = Collections.class.getDeclaredClasses();
            for (Class c : declaredClasses) {
                if ("java.util.Collections$UnmodifiableList".equals(c.getName())) {
                    Field listField = c.getDeclaredField("list");
                    listField.setAccessible(true);
                    List<TypeAdapterFactory> list = (List<TypeAdapterFactory>) listField.get(o);
                    int i = list.indexOf(ObjectTypeAdapter.FACTORY);
                    list.set(i, MapTypeAdapter.FACTORY);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson;
    }

}
