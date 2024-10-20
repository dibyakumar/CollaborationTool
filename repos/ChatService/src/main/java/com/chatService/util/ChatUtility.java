package com.chatService.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.chatService.Exception.ServiceException;
import com.chatService.model.MailRequest;

@Service
public class ChatUtility {
	
	@Autowired
	private WebClient.Builder webClient;

	public void notifyByMail(String email, String subject, String htmlContent) {
		webClient.build()
		.post()
		.uri("http://localhost:8083/mail/notify")
		.bodyValue(MailRequest.builder().to(email).subject(subject).content(htmlContent).build())
		.retrieve()
		.bodyToMono(Void.class) // this specifies we dont need any thing in response (Void) just only a success status 
		.subscribe();
	}
	

	public String getCurrentUsername() {
	     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    if (authentication != null && authentication.isAuthenticated()) {
	        Object principal = authentication.getPrincipal();
	        
	        if (principal instanceof UserDetails) {
	            return ((UserDetails) principal).getUsername(); // return the username
	        } else {
	            return principal.toString(); // In case of anonymous or non-UserDetails authentication
	        }
	    }
	    
	    throw new ServiceException("user Not found",HttpStatusCode.valueOf(400));
	}
}
