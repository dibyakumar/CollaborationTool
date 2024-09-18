package com.user.microservice.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessResponse {
	private String message;
}
