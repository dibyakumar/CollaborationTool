package com.project.microservice.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.project.microservice.client.AuthorizationRequestInterceptor;
import com.project.microservice.service.CustomUserDetailsService;

@Configuration
public class ApplicationConfig {
	
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
	 WebClient.Builder webClient(){
		return WebClient.builder();
	}
	
	@Bean
	Cache<String, String> localCache(){
		return Caffeine.newBuilder()
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .maximumSize(1000) // Optional: limit cache size
                .build();
	}
	
	@Bean
	@Lazy
	AuthorizationRequestInterceptor authorizationRequestInterceptor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String token = (String)authentication.getDetails();
		return new AuthorizationRequestInterceptor(token);
	}
}
