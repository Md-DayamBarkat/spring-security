package com.app.security.spring_security.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.app.security.spring_security.dto.AuthRequest;
import com.app.security.spring_security.dto.AuthResponse;
import com.app.security.spring_security.security.JwtUtil;

@Service
public class AuthService {

	private final AuthenticationManager authenticationManager;

	private final CustomUserDetailsService customUserDetailsService;

	private final JwtUtil jwtUtil;

	public AuthService(
		AuthenticationManager authenticationManager,
		CustomUserDetailsService customUserDetailsService,
		JwtUtil jwtUtil
	) {
		this.authenticationManager = authenticationManager;
		this.customUserDetailsService = customUserDetailsService;
		this.jwtUtil = jwtUtil;
	}

	public AuthResponse login(AuthRequest request) {
		Authentication authentication = this.authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
		);

		UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(authentication.getName());
		String token = this.jwtUtil.generateToken(userDetails);
		return new AuthResponse(token, "Bearer");
	}
}
