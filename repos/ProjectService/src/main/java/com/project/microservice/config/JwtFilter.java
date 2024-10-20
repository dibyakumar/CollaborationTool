package com.project.microservice.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.microservice.service.CustomUserDetailsService;
import com.project.microservice.service.JwtConfgService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtConfgService jwtService;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader("Authorization");
		if (null == header && !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
		}

		String token = header.substring(7);
		try {
			String userName = jwtService.extractUserName(token);

			UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

			if (userDetails.getUsername().equals(userName)
					&& null == SecurityContextHolder.getContext().getAuthentication()) {

				if (jwtService.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken userPasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					userPasswordAuthenticationToken
							.setDetails(token);
					SecurityContextHolder.getContext().setAuthentication(userPasswordAuthenticationToken);
				}
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			response.getWriter().write("{\"error\": \"Invalid or expired token\"}");
			return;
		}
		filterChain.doFilter(request, response);
	}

}
