package com.integration.utils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.integration.config.Audience;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
	private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);
	
	private static RedisTemplate<String,Object> redisTemplate2;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;


	private static Audience audience;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		redisTemplate2 = redisTemplate;
	}
	
	/**
	 * 批量删除对应的value
	 *
	 * @param keys
	 */
	public static void remove(String... keys) {
		keys = formatKey(keys);
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 批量删除key
	 *
	 * @param pattern
	 */
	public static void removePattern(String pattern) {
		Set<String> keys = redisTemplate2.keys(pattern);
		if (keys.size() > 0) {
			keys = formatKey(keys);			
			redisTemplate2.delete(keys);
		}
	}

	/**
	 * 删除对应的value
	 *
	 * @param key
	 */
	public static void remove(Object keyObj) {
		if (keyObj==null) 
			return ;
		String key = formatKey(keyObj.toString());
		if (exists(key)) {
			redisTemplate2.delete(key);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 *
	 * @param key
	 * @return
	 */
	public static boolean exists(String key) {
		key = formatKey(key);
		return redisTemplate2.hasKey(key);
	}

	/**
	 * 读取缓存
	 *
	 * @param key
	 * @return
	 */
	public static Object get(Object keyObj) {
		if (keyObj==null) 
			return null;
		String key = formatKey(keyObj.toString());
		Object result = null;
		ValueOperations<String, Object> operations = redisTemplate2.opsForValue();
		result = operations.get(key);
		return result;
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean set(Object keyObj, Object value) {
		if (keyObj==null) 
			return false;
		String key = formatKey(keyObj.toString());
		boolean result = false;
		try {
			int expires = audience.getExpiresSecond();
			if(expires <= 0){
				//默认2小时
				expires = 60 * 60 * 2;
			}
			ValueOperations<String, Object> operations = redisTemplate2.opsForValue();
			operations.set(key, value, expires, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 更新缓存过期时间
	 *
	 * @return
	 */
	public static boolean expires(Object keyObj) {
		if (keyObj==null){
			return false;
		}
		String key = formatKey(keyObj.toString());
		boolean result = false;
		try {
			int expires = audience.getExpiresSecond();
			if(expires <= 0){
				//默认2小时
				expires = 60 * 60 * 2;
			}
			result = redisTemplate2.expire(key, expires, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean set(Object keyObj, Object value, Long expireTime) {
		if (keyObj==null) 
			return false;
		String key = formatKey(keyObj.toString());
		boolean result = false;
		try {
			ValueOperations<String, Object> operations = redisTemplate2.opsForValue();
			operations.set(key, value);
			redisTemplate2.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}
	
	public static void clear() {
	  //回调函数
	    redisTemplate2.execute((RedisCallback)collection->{
            collection.flushDb();
            return  null;
        });
	}
	
	public static int size() {
		Set<String> keys = redisTemplate2.keys("*");
		return keys.size();
	}

	public static int size(String keyPre) {
		Set<String> keys = redisTemplate2.keys(keyPre);
		return keys.size();
	}

	public static Set<String> keys(String keyPre) {
		return redisTemplate2.keys(keyPre);
	}

	/**
	 * redis不允许key里有_，换成-
	 * @param key
	 * @return
	 */
	public static String formatKey(String key) {
		if (StringUtils.isNotEmpty(key)) {
			return key.replaceAll("_", "-");
		}
		return null;
	}
	
	/**
	 * redis不允许key里有_，换成-
	 * @param key
	 * @return
	 */
	public static String[] formatKey(String... key) {
		if (key!=null && key.length>0) {
			for (String string : key) {
				string = formatKey(string);
			}
		}
		return key;
	}
	
	/**
	 * redis不允许key里有_，换成-
	 * @param key
	 * @return
	 */
	public static Set<String> formatKey(Set<String> keys) {
		if (keys!=null && keys.size()>0) {
			for (String string : keys) {
				string = formatKey(string.toString());
			}
		}
		return keys;
	}

	@Autowired
	public void setAudience(Audience audience) {
		RedisUtils.audience = audience;
	}

	/**
	 * list方式存储key
	 * @param key
	 * @param token
	 * @return
	 */
	public static boolean setAdd(String key, String token){
		return redisTemplate2.opsForSet().add(key, token) > 0;
	}

	/**
	 * 判断list列表是否包含 指定值
	 * @param key
	 * @param token
	 * @return
	 */
	public static boolean setContains(String key, String token){
		if(exists(key)){
			return redisTemplate2.opsForSet().isMember(key, token);
		}
		return false;
	}
	/**
	 * 删除列表中指定值
	 * @param key
	 * @param token
	 * @return
	 */
	public static boolean setRemove(String key, String token){
		if(exists(key)){
			return redisTemplate2.opsForSet().remove(key,  token) > 0;
		}
		return false;
	}


	/**
	 * 获得分布式锁
	 */
	public static boolean getLock(String lockId, long millisecond) {
		if (lockId==null){
			return false;
		}
		String key = formatKey(lockId);
		Boolean success = redisTemplate2.opsForValue().setIfAbsent(key, "lock",
				millisecond, TimeUnit.MILLISECONDS);
		return success != null && success;
	}

	/**
	 * 释放获取
	 * @param lockId
	 */
	public static void releaseLock(String lockId) {
		remove(lockId);
	}

	/**
	 * 存储list
	 * @param key
	 * @param value
	 */
	public static void setList(String key,Object value){
		redisTemplate2.opsForList().rightPush(key, value);
	}

	/**
	 * 获取指定区间范围的数据
	 *
	 * @param key
	 * @return
	 */
	public static Object getList(String key){
		return redisTemplate2.opsForList().leftPop(key);
	}

	/**
	 * 获取list队列长度
	 *
	 * @param key
	 * @return
	 */
	public static long getListLen(String key){
		return redisTemplate2.opsForList().size(key);
	}
}
