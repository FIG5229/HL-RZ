package com.integration.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json的工具类
 * 
 * @author suozhaoyu
 * @version 0.0.1
 * @since 0.0.1
 */
public class JsonUtils {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	/**
	 * map转换为json格式的字符串
	 * 
	 * @param map
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String mapToString(Map<String, ?> map){
		return objectToString(map);
	}
	
	/**
	 * obj转换为json格式的字符串
	 * 
	 * @param obj
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String objectToString(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
