package com.user.microservice.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticateResponse {
	private String token;
}
