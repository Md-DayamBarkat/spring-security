package com.app.security.spring_security.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public enum Role {

	USER(new LinkedHashSet<Permission>(Arrays.asList(Permission.WEATHER_READ))),
	ADMIN(new LinkedHashSet<Permission>(Arrays.asList(
		Permission.WEATHER_READ,
		Permission.WEATHER_WRITE,
		Permission.WEATHER_DELETE,
		Permission.USER_CREATE
	)));

	private final Set<Permission> permissions;

	Role(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public Set<Permission> getPermissions() {
		return Collections.unmodifiableSet(this.permissions);
	}

	public static Role fromValue(String value) {
		return Role.valueOf(value.trim().toUpperCase(Locale.ENGLISH));
	}
}
