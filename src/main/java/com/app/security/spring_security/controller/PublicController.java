package com.app.security.spring_security.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.security.spring_security.service.WeatherService;

@RestController
@RequestMapping("/api/public")
public class PublicController {

	private final WeatherService weatherService;

	public PublicController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@GetMapping("/health")
	public ResponseEntity<Map<String, Object>> health() {
		return ResponseEntity.ok(this.weatherService.health());
	}
}
