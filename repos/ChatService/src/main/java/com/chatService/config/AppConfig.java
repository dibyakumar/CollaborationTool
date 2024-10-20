package com.chatService.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.chatService.service.CustomUserDetailsService;

@Configuration
public class AppConfig {
	
	
	 	@Bean
	     KafkaAdmin kafkaAdmin() {
	        Map<String, Object> configs = new HashMap<>();
	        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "pkc-921jm.us-east-2.aws.confluent.cloud:9092");
	        configs.put("sasl.mechanism", "PLAIN");
	        configs.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username='GPSU43ZGKCRGREVV' password='dlhc1aKX/3OZvBgN8VV4kz8lVBgTTBJv83YNBk0iJCgcPCatKVcRn+pOXCVyGDoJ';");
	        configs.put("security.protocol", "SASL_SSL");
	        return new KafkaAdmin(configs);
	    }
	 	
	 	
	 	@Autowired
		private CustomUserDetailsService userDetailsService;
		
		@Bean
		 DaoAuthenticationProvider getAuthenticationProvider() {
			DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
			daoAuthenticationProvider.setUserDetailsService(userDetailsService);
			daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
			return daoAuthenticationProvider;
		}
		
		@Bean
		 PasswordEncoder getPasswordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		 @Bean
		    public ProducerFactory<String, String> producerFactory() {
		        Map<String, Object> configProps = new HashMap<>();
		        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "pkc-921jm.us-east-2.aws.confluent.cloud:9092");
		        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		        configProps.put("security.protocol", "SASL_SSL");
		        configProps.put("sasl.mechanism", "PLAIN");
		        configProps.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username='GPSU43ZGKCRGREVV' password='dlhc1aKX/3OZvBgN8VV4kz8lVBgTTBJv83YNBk0iJCgcPCatKVcRn+pOXCVyGDoJ';");

		        return new DefaultKafkaProducerFactory<>(configProps);
		    }

		    @Bean
		    public KafkaTemplate<String, String> kafkaTemplate() {
		        return new KafkaTemplate<>(producerFactory());
		    }
		    
		 
}
