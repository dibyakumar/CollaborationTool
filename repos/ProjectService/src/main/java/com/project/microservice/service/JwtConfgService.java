package com.project.microservice.service;

import java.security.Key;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtConfgService {
	
	// secrete key same as user service
	private static final String SECRETE = "Ti8rDVaXBmB8hUOFU3OVAklmA8HG5lMKPv1q5Jy1R7WPsD38m8XAv9QZWLgGS7/j"; // base64
	private static final long JWT_EXPIRATION = 900000L;
	
	public boolean validateToken(String token,UserDetails userDetails) {
		String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && isTokenValid(token));
	}
	
	public boolean isTokenValid(String token) {
		Key key = Keys.hmacShaKeyFor(SECRETE.getBytes());
		try {
			Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		}catch(JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	public String extractUserName(String token) {
		Key key = Keys.hmacShaKeyFor(SECRETE.getBytes());
		return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}
}
