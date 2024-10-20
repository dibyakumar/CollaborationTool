package com.user.microservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.microservice.models.MailNotification;
import com.user.microservice.models.SuccessResponse;
import com.user.microservice.services.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/mail")
public class NotificationController {
	
	@Autowired
	private MailService mailService;
	
	@PostMapping("/notify")
	public ResponseEntity<SuccessResponse> postMethodName(@RequestBody MailNotification mailNotification) {
		return ResponseEntity.ok(mailService.sendEmail(mailNotification.getTo(), mailNotification.getSubject(), mailNotification.getContent()));
	}
	
}
