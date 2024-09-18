package com.user.microservice.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.user.microservice.models.ErrorResponse;

@ControllerAdvice
public class ExceptionHandlerUniversal {

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ErrorResponse> serviceException(ServiceException exception){
		return new ResponseEntity<ErrorResponse>(ErrorResponse.builder().message(exception.getMessage()).statusCode(exception.statusCode).timeInMilisec(System.currentTimeMillis()+"").build(),exception.statusCode);
	}
}
