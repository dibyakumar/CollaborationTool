package com.project.microservice.model;

import org.springframework.http.HttpStatusCode;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ErrorResponse {
	private HttpStatusCode statusCode;
	private String message;
	private String timeInMilisec;
}
