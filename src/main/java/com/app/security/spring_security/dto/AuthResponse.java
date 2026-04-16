package com.app.security.spring_security.dto;

public class AuthResponse {

	private String token;

	private String tokenType;

	public AuthResponse() {
	}

	public AuthResponse(String token, String tokenType) {
		this.token = token;
		this.tokenType = tokenType;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenType() {
		return this.tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
}
