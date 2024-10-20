package com.user.microservice.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersDto {
	String userName;
	String email;
	String organization;
}
