package com.app.security.spring_security.dto;

public class WeatherRequest {

	private String city;

	private String condition;

	private double temperatureCelsius;

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
