package com.app.security.spring_security.enums;

public enum Permission {

	WEATHER_READ("WEATHER_READ"),
	WEATHER_WRITE("WEATHER_WRITE"),
	WEATHER_DELETE("WEATHER_DELETE"),
	USER_CREATE("USER_CREATE");

	private final String authority;

	Permission(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return this.authority;
	}
}
