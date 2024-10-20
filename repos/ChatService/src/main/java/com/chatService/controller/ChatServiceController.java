package com.chatService.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatService.entity.Message;
import com.chatService.model.MessagesDto;
import com.chatService.model.SuccessResponse;
import com.chatService.service.ChatService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/v1/chat")
public class ChatServiceController {
	
	@Autowired
	private ChatService chatService;
	
	@PostMapping("/createTopic")
	public ResponseEntity<SuccessResponse> createTopic(@RequestParam String topicName) {
		return new ResponseEntity<SuccessResponse>(chatService.createTopicInKafka(topicName),HttpStatusCode.valueOf(200));
	}
	
	@PostMapping("/send")
	public ResponseEntity<SuccessResponse> sendMessage(@RequestParam String message){
		return new ResponseEntity<SuccessResponse>(chatService.sendMessage(message),HttpStatusCode.valueOf(200));
	}
	
	@GetMapping("/message/{teamId}")
	public ResponseEntity<List<MessagesDto>> getMessage(@PathVariable Long teamId){
		return new ResponseEntity<List<MessagesDto>>(chatService.getMessage(teamId),HttpStatusCode.valueOf(200));
	}
	
	
	
}
