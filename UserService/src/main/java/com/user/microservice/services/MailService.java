package com.user.microservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.user.microservice.models.SuccessResponse;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String userEmail;
	
	private static final Logger logger =  LoggerFactory.getLogger(MailService.class);
	
	 public SuccessResponse sendEmail(String to, String subject, String htmlContent) {
	  
		 try{
			 // we can use this whenever we want to send a simple text email . 
		 //  SimpleMailMessage message = new SimpleMailMessage();
		// if you want to send as a HTML text you can use mimeMailMessage
		 MimeMessage message = javaMailSender.createMimeMessage();
		 MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
		 helper.setFrom(userEmail);  // Sender's email
		 helper.setTo(to);                        // Recipient's email
		 helper.setSubject(subject);              // Email subject
		 helper.setText(htmlContent,true);                    // Email body (content)

	        javaMailSender.send(message);
	        return SuccessResponse.builder().message("Mail Sent to "+to).build();
		 }catch (Exception e) {
			 logger.warn("Mail service failed");
		}
	        
	        return null;
	    }
}
