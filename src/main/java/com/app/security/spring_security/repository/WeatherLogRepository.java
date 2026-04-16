package com.app.security.spring_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.security.spring_security.entity.WeatherLog;

public interface WeatherLogRepository extends JpaRepository<WeatherLog, Long> {
}
