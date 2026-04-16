package com.app.security.spring_security.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import com.app.security.spring_security.dto.WeatherRequest;
import com.app.security.spring_security.dto.WeatherResponse;
import com.app.security.spring_security.entity.WeatherLog;
import com.app.security.spring_security.repository.WeatherLogRepository;

@Service
public class WeatherService {

	private final Map<String, WeatherResponse> weatherStore = new ConcurrentHashMap<String, WeatherResponse>();

	private final WeatherLogRepository weatherLogRepository;

	public WeatherService(WeatherLogRepository weatherLogRepository) {
		this.weatherLogRepository = weatherLogRepository;
		this.weatherStore.put("Bangalore", new WeatherResponse("Bangalore", "Sunny", 29.5));
		this.weatherStore.put("Hyderabad", new WeatherResponse("Hyderabad", "Cloudy", 27.0));
	}

	public List<WeatherResponse> getAllWeather() {
		return new ArrayList<WeatherResponse>(this.weatherStore.values());
	}

	public WeatherResponse saveWeather(WeatherRequest request, String performedBy) {
		WeatherResponse response = new WeatherResponse(
			request.getCity(),
			request.getCondition(),
			request.getTemperatureCelsius()
		);
		this.weatherStore.put(request.getCity(), response);
		this.weatherLogRepository.save(new WeatherLog("UPSERT", request.getCity(), performedBy));
		return response;
	}

	public void deleteWeather(String city, String performedBy) {
		this.weatherStore.remove(city);
		this.weatherLogRepository.save(new WeatherLog("DELETE", city, performedBy));
	}

	@PostAuthorize("returnObject == null || returnObject.performedBy == authentication.name || hasRole('ADMIN')")
	public WeatherLog getWeatherLog(Long logId) {
		return this.weatherLogRepository.findById(logId).orElse(null);
	}

	public Map<String, Object> health() {
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("status", "UP");
		response.put("service", "weather-service");
		return response;
	}
}
