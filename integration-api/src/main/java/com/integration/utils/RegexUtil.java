package com.integration.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * 正则表达式验证
 * 
 * @author dell
 *
 */
public class RegexUtil {
	
	private static final Map<String, String> REGEX_TIME = new HashMap<>();
	static {
		REGEX_TIME.put("^[0-9]{4}-(?:0?[1-9]|1[0-2])-(?:0?[1-9]|[1-2][0-9]|3[0-1])\\s(?:0?[0-9]|1[0-9]|2[0-3]):(?:0?[0-9]|[1-5][0-9]):(?:0?[0-9]|[1-5][0-9])$"
				, "yyyy-MM-dd HH:mm:ss");
		REGEX_TIME.put("^[0-9]{4}/(?:0?[1-9]|1[0-2])/(?:0?[1-9]|[1-2][0-9]|3[0-1])\\s(?:0?[0-9]|1[0-9]|2[0-3]):(?:0?[0-9]|[1-5][0-9]):(?:0?[0-9]|[1-5][0-9])$"
				, "yyyy/MM/dd HH:mm:ss");
		REGEX_TIME.put("^[0-9]{4}(?:0?[1-9]|1[0-2])(?:0?[1-9]|[1-2][0-9]|3[0-1])\\s(?:0?[0-9]|1[0-9]|2[0-3])(?:0?[0-9]|[1-5][0-9])(?:0?[0-9]|[1-5][0-9])$"
				, "yyyyMMdd HHmmss");
	}
	
	/**
	 * 验证是否为时间格式
	 * 
	 * @param time：时间格式的字符串
	 * @return
	 */
	public static String validTime(String time) {
		if(null == time) {
			return "";
		}
		
		Optional<String> regex = REGEX_TIME.entrySet().stream()
				.filter(m -> Pattern.matches(m.getKey(), time))
				.map(m -> m.getValue())
				.findAny();
		
		return regex.orElse("");
	}
	
	/**
	 * 验证字符串是否是正整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean validPositiveInt(String str) {
		String regex = "^[1-9]\\d*$";
		return Pattern.matches(regex, str);
	}
}
