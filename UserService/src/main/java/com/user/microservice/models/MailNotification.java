package com.user.microservice.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailNotification {
	private String to;
	private String subject;
	private String content;
}
