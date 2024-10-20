package com.chatService.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessResponse {
	private String message;
}
