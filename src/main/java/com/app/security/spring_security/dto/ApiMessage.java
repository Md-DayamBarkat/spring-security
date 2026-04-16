package com.app.security.spring_security.dto;

public class ApiMessage {

	private String message;

	public ApiMessage() {
	}

	public ApiMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
