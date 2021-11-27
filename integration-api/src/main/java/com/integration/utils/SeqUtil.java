package com.integration.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 序列工具
 *
 * @author dell
 *
 */
@Component
public class SeqUtil {

	@Autowired
	private SnowflakeComponent snowflakeComponent;

	private static SnowflakeComponent snowflakeComponent2;

	@PostConstruct
	public void init() {
		snowflakeComponent2 = snowflakeComponent;
	}

	public static Long nextId() {
		return snowflakeComponent2.getInstance().nextId();
	}

	public static String getId() {
		return nextId().toString();
	}
}
