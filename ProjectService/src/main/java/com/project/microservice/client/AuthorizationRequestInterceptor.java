package com.project.microservice.client;

import com.project.microservice.constants.ProjectConstants;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class AuthorizationRequestInterceptor implements RequestInterceptor{

	
	private final String token;
	
	public AuthorizationRequestInterceptor(String token) {
		this.token = token;
	}
	
	@Override
	public void apply(RequestTemplate template) {
		template.header(ProjectConstants.AUTH, token);
	}

}
