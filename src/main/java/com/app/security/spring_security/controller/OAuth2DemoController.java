package com.app.security.spring_security.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth-demo")
public class OAuth2DemoController {

	@GetMapping("/public")
	public ResponseEntity<Map<String, Object>> publicApi() {
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "This is the public OAuth2 demo API");
		response.put("oauth2Enabled", "Set app.security.oauth2.enabled=true and add Google client credentials");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/private")
	public ResponseEntity<Map<String, Object>> privateApi() {
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "This is the private OAuth2 demo API");
		response.put("note", "When OAuth2 is enabled, Google login secures this endpoint");
		return ResponseEntity.ok(response);
	}
}
