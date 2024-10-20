package com.chatService.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailRequest {
	private String to;
	private String subject;
	private String content;
}
