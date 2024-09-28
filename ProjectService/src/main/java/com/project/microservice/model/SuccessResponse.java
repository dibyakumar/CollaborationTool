package com.project.microservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessResponse {
	private String message;
}
