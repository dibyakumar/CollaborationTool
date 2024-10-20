package com.chatService.config;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
public class DynamicTopicName {
	private String topicName="alert-topic";

	public String getTopicName() {
		  System.out.println("Dynamic topic name called!");
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	
	
}
