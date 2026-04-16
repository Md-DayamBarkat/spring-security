package com.app.security.spring_security.dto;

public class WeatherResponse {

	private String city;

	private String condition;

	private double temperatureCelsius;

	public WeatherResponse() {
	}

	public WeatherResponse(String city, String condition, double temperatureCelsius) {
		this.city = city;
		this.condition = condition;
		this.temperatureCelsius = temperatureCelsius;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public double getTemperatureCelsius() {
		return this.temperatureCelsius;
	}

	public void setTemperatureCelsius(double temperatureCelsius) {
		this.temperatureCelsius = temperatureCelsius;
	}
}
