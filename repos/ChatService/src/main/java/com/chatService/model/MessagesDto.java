package com.chatService.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessagesDto {
	private String text;
	private String senderName;
}
