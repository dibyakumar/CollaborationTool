package com.user.microservice.controller;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.microservice.Exception.ServiceException;
import com.user.microservice.models.AuthenticateRequest;
import com.user.microservice.models.AuthenticateResponse;
import com.user.microservice.models.RegisterRequest;
import com.user.microservice.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class UserAuthenticationController {
	
	
	@Autowired
	private AuthenticationService authService;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
		return new ResponseEntity<String>(authService.registerUser(registerRequest),HttpStatus.OK);
	}
	
	@PostMapping("/generateToken")
	public ResponseEntity<AuthenticateResponse> generateToken(@RequestBody AuthenticateRequest authRequest){
		AuthenticateResponse token;
		try {
			 token = authService.generateToken(authRequest);
		}catch (Exception e) {
			throw new ServiceException("Invalid user", HttpStatusCode.valueOf(403));
		}
		return new ResponseEntity<AuthenticateResponse>(token,HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticateResponse> login(@RequestBody AuthenticateRequest authRequest){
		return new ResponseEntity<AuthenticateResponse>(authService.generateToken(authRequest),HttpStatus.OK);
	}
	
}
