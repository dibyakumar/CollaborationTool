package com.user.microservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.microservice.Repository.UserRepository;
import com.user.microservice.entity.User;
import com.user.microservice.models.AuthenticateRequest;
import com.user.microservice.models.AuthenticateResponse;
import com.user.microservice.models.RegisterRequest;

@Service
public class AuthenticationService {

	@Autowired
	private UserRepository urepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private JwtConfService jwtService;

	
	public String registerUser(RegisterRequest registerRequest) {
		
		User user = new User();
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setUserName(registerRequest.getUsername());
		user.setRole(registerRequest.getRole());
		
		urepo.save(user);
		return "Registered";
	}

	public AuthenticateResponse generateToken(AuthenticateRequest authRequest) {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			String token = jwtService.generateToken(authRequest.getUsername());
			return AuthenticateResponse.builder().token(token).build();
			
		}catch (AuthenticationException e) {
			throw new RuntimeException("Invalid User");
		}
	}
	
	
}
