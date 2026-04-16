package com.app.security.spring_security.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.security.spring_security.dto.ApiMessage;
import com.app.security.spring_security.dto.WeatherRequest;
import com.app.security.spring_security.dto.WeatherResponse;
import com.app.security.spring_security.entity.WeatherLog;
import com.app.security.spring_security.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

	private final WeatherService weatherService;

	public WeatherController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@PreAuthorize("hasAuthority('WEATHER_READ')")
	@GetMapping
	public ResponseEntity<List<WeatherResponse>> getWeather() {
		return ResponseEntity.ok(this.weatherService.getAllWeather());
	}

	@PreAuthorize("hasAuthority('WEATHER_WRITE')")
	@PostMapping
	public ResponseEntity<WeatherResponse> addWeather(@RequestBody WeatherRequest request, Principal principal) {
		return ResponseEntity.ok(this.weatherService.saveWeather(request, principal.getName()));
	}

	@PreAuthorize("hasAuthority('WEATHER_DELETE')")
	@DeleteMapping("/{city}")
	public ResponseEntity<ApiMessage> deleteWeather(@PathVariable String city, Principal principal) {
		this.weatherService.deleteWeather(city, principal.getName());
		return ResponseEntity.ok(new ApiMessage("Weather deleted for city: " + city));
	}

	@GetMapping("/logs/{logId}")
	public ResponseEntity<?> getWeatherLog(@PathVariable Long logId) {
		WeatherLog log = this.weatherService.getWeatherLog(logId);
		if (log == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiMessage("Weather log not found"));
		}
		return ResponseEntity.ok(log);
	}
}
