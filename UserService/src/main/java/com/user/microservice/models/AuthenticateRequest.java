package com.user.microservice.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticateRequest {
	private String username;
	private String password;
}
