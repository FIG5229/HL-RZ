package com.integration.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.lang.reflect.Method;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

	private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

	// 生产KEY策略
	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object arg0, Method arg1, Object... arg2) {
				StringBuilder sb = new StringBuilder();
				sb.append(arg0.getClass().getName());
				sb.append(arg1.getName());
				for (Object obj : arg2) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}

	// 管理缓存
	// @SuppressWarnings("rawtypes")
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		return RedisCacheManager.builder(connectionFactory).build();
	}

	// redisTemplate配置
	@Bean
	public RedisTemplate<String, String> redisTemplate(
			RedisConnectionFactory factory) {
		StringRedisTemplate template = new StringRedisTemplate(factory);
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}

	// 数据库连接池
	// @Bean
	// public JedisConnectionFactory redisConnectionFactory() {
	// JedisConnectionFactory factory = new JedisConnectionFactory();
	// factory.setHostName(host);
	// factory.setPort(port);
	// factory.setPassword(password);
	// factory.setTimeout(timeout); //设置连接超时时间
	// return factory;
	// } 　　 不配置端口默认为6379

	/*
	 * @Autowired RedisTemplate redisTemplate;
	 * 
	 * @Bean public ValueOperations<String,Object> vos(){ return
	 * redisTemplate.opsForValue(); }
	 */

}