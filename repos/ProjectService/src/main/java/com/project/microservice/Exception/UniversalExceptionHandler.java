package com.project.microservice.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.project.microservice.model.ErrorResponse;

@ControllerAdvice
public class UniversalExceptionHandler {
		@ExceptionHandler(ServiceException.class)
		public ResponseEntity<ErrorResponse> serviceException(ServiceException exception){
			return new ResponseEntity<ErrorResponse>(ErrorResponse.builder().message(exception.getMessage()).statusCode(exception.statusCode).timeInMilisec(System.currentTimeMillis()+"").build(),exception.statusCode);
		}

}
