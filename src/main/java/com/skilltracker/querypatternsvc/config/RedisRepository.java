package com.skilltracker.querypatternsvc.config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.skilltracker.querypatternsvc.bean.SkillTrackerResponseBean;

@Configuration
public class RedisRepository {

	@Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.password}")
	private String redisPassword;
	@Value("${spring.redis.port}")
	private int redisPort;
	@Value("${spring.redis.ssl}")
	private boolean redisSSL;

	@Value("${spring.redis.timeOut}")
	private int timeout;

	private RedisTemplate<String, Object> template;

	@Value("${spring.redis.cache.expiryTime}")
	private int expiryTime;

	@Bean
	@Primary
	LettuceConnectionFactory redisConnection() {
		RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
		redisConfig.setHostName(redisHost);
		redisConfig.setPassword(RedisPassword.of(redisPassword));
		redisConfig.setPort(redisPort);
		LettucePoolingClientConfigurationBuilder clientConfigBuilder = LettucePoolingClientConfiguration.builder();
		if (redisSSL) {
			clientConfigBuilder.useSsl();
		}
		clientConfigBuilder.commandTimeout(Duration.ofSeconds(timeout));
		clientConfigBuilder.poolConfig(new GenericObjectPoolConfig());
		LettuceClientConfiguration clientConfig = clientConfigBuilder.build();
		return new LettuceConnectionFactory(redisConfig, clientConfig);
	}

	@Bean("skillTracker")
	public RedisTemplate<String, Object> skillTrackerRedisTemplate(
			@Qualifier("redisConnection") LettuceConnectionFactory factory) {
		template = new RedisTemplate<>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new GenericToStringSerializer<>(Object.class));
		template.setValueSerializer(new Jackson2JsonRedisSerializer<>(SkillTrackerResponseBean.class));
		template.setConnectionFactory(factory);
		return template;
	}

	public Object getValue(String key) {
		return template.opsForValue().get(key);
	}

	public void setValue(String key, Object value) {
		template.opsForValue().set(key, value);
		template.expire(key, expiryTime, TimeUnit.MINUTES);
	}

	public boolean delete(String key) {
		return template.delete(key);
	}

	public Object getValue(RedisTemplate<String, Object> redisTemplate, String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public void setValue(RedisTemplate<String, Object> redisTemplate, String key, Object value, int expiryTime) {
		redisTemplate.opsForValue().set(key, value);
		redisTemplate.expire(key, expiryTime, TimeUnit.MINUTES);
	}

	public void removeKey(RedisTemplate<String, Object> redisTemplate, String key) {
		redisTemplate.expire(key, 1, TimeUnit.NANOSECONDS);
	}

	public void flushAll(RedisTemplate<String, Object> redisTemplate) {
		redisTemplate.getConnectionFactory().getConnection().flushAll();
	}

}
