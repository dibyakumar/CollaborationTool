package com.chatService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfig {

	@Bean
	 WebClient.Builder webClient(){
		return WebClient.builder();
	}
	
}
