package com.skilltracker.querypatternsvc.kafka.config;

import com.skilltracker.querypatternsvc.bean.repo.SkillTracker;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import com.common.dto.SkillTrackerCommon;

@Configuration
public class KafkaConsumerConfig {
	
	@Bean
	public ConsumerFactory<String, SkillTrackerCommon> consumerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.10:9092");
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "SKILLTRACKER");
		return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new JsonDeserializer<>(SkillTrackerCommon.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, SkillTrackerCommon> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, SkillTrackerCommon> factory = new ConcurrentKafkaListenerContainerFactory<String, SkillTrackerCommon>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
	


}
