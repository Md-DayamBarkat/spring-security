package com.app.security.spring_security.controller;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LearningController {

	@GetMapping("/public/hello")
	public Map<String, Object> publicHello() {
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "This is a public endpoint");
		response.put("security", "No authentication required");
		return response;
	}

	@GetMapping("/user/profile")
	public Map<String, Object> userProfile(Principal principal, Authentication authentication) {
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "This is a protected endpoint");
		response.put("username", principal.getName());
		response.put("authorities", authentication.getAuthorities());
		return response;
	}

	@GetMapping("/user/dashboard")
	public Map<String, Object> userDashboard(Principal principal) {
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "This endpoint is available only to users with ROLE_USER");
		response.put("username", principal.getName());
		response.put("requiredRole", "ROLE_USER");
		return response;
	}

	@GetMapping("/admin/dashboard")
	public Map<String, Object> adminDashboard(Principal principal) {
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "This endpoint is available only to users with ROLE_ADMIN");
		response.put("username", principal.getName());
		response.put("requiredRole", "ROLE_ADMIN");
		return response;
	}
}
