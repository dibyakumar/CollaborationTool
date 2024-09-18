package com.user.microservice.Exception;

import org.springframework.http.HttpStatusCode;

public class ServiceException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public HttpStatusCode statusCode;
	
	public ServiceException(String message,HttpStatusCode code) {
		super(message);
		this.statusCode = code;
	}

}
