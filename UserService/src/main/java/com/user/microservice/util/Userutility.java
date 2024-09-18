package com.user.microservice.util;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.user.microservice.Exception.ServiceException;
import com.user.microservice.Repository.UserRepository;
import com.user.microservice.entity.User;

@Service
public class Userutility {

	@Autowired
	private UserRepository urepo;
	
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
	
	public String generateOtp() {
		return (1000+new Random().nextInt(9999))+"";
	}
	
	public User getUserFromUserName() {
		return urepo.findByUserName(getCurrentUsername());
	}
}
