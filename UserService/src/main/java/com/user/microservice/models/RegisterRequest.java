package com.user.microservice.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
	private String username;
	private String password;
	private Role role;
	private String email;
	private String organization;
}
