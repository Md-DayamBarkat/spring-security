package com.app.security.spring_security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.app.security.spring_security.entity.WeatherLog;
import com.app.security.spring_security.repository.WeatherLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityFlowTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WeatherLogRepository weatherLogRepository;

	@Test
	void publicHealthShouldBeAccessibleWithoutAuthentication() throws Exception {
		this.mockMvc.perform(get("/api/public/health"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value("UP"));
	}

	@Test
	void weatherEndpointShouldRejectAnonymousUser() throws Exception {
		this.mockMvc.perform(get("/api/weather"))
			.andExpect(status().isUnauthorized());
	}

	@Test
	void loginShouldReturnJwtToken() throws Exception {
		this.mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(loginPayload("admin", "admin123"))))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.token").isNotEmpty())
			.andExpect(jsonPath("$.tokenType").value("Bearer"));
	}

	@Test
	void jwtTokenShouldAllowReadingWeather() throws Exception {
		String token = obtainToken("weatheruser", "password");
		this.mockMvc.perform(get("/api/weather").header("Authorization", bearer(token)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].city").exists());
	}

	@Test
	void normalUserShouldNotBeAbleToWriteWeather() throws Exception {
		String token = obtainToken("weatheruser", "password");
		this.mockMvc.perform(post("/api/weather")
				.header("Authorization", bearer(token))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"city\":\"Chennai\",\"condition\":\"Rainy\",\"temperatureCelsius\":24.3}"))
			.andExpect(status().isForbidden());
	}

	@Test
	void adminShouldBeAbleToWriteWeatherAndDeleteWeather() throws Exception {
		String token = obtainToken("admin", "admin123");
		this.mockMvc.perform(post("/api/weather")
				.header("Authorization", bearer(token))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"city\":\"Chennai\",\"condition\":\"Rainy\",\"temperatureCelsius\":24.3}"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.city").value("Chennai"));

		this.mockMvc.perform(delete("/api/weather/Chennai").header("Authorization", bearer(token)))
			.andExpect(status().isOk());
	}

	@Test
	void publicRegisterShouldCreateNewUser() throws Exception {
		String username = "learner_" + System.currentTimeMillis();
		this.mockMvc.perform(post("/api/users/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"" + username + "\",\"password\":\"secret123\"}"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.username").value(username))
			.andExpect(jsonPath("$.role").value("USER"));
	}

	@Test
	void adminCreationApiShouldRequireUserCreateAuthority() throws Exception {
		String userToken = obtainToken("weatheruser", "password");
		this.mockMvc.perform(post("/api/users/admin/create")
				.header("Authorization", bearer(userToken))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"blocked_user\",\"password\":\"secret123\",\"role\":\"USER\"}"))
			.andExpect(status().isForbidden());

		String adminToken = obtainToken("admin", "admin123");
		String username = "managed_" + System.currentTimeMillis();
		this.mockMvc.perform(post("/api/users/admin/create")
				.header("Authorization", bearer(adminToken))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"" + username + "\",\"password\":\"secret123\",\"role\":\"ADMIN\"}"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.username").value(username))
			.andExpect(jsonPath("$.role").value("ADMIN"));
	}

	@Test
	void postAuthorizeShouldBlockOtherUsersFromReadingWeatherLog() throws Exception {
		String adminToken = obtainToken("admin", "admin123");
		this.mockMvc.perform(post("/api/weather")
				.header("Authorization", bearer(adminToken))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"city\":\"Mumbai\",\"condition\":\"Humid\",\"temperatureCelsius\":31.0}"))
			.andExpect(status().isOk());

		WeatherLog latestLog = this.weatherLogRepository.findAll().get(this.weatherLogRepository.findAll().size() - 1);

		String userToken = obtainToken("weatheruser", "password");
		this.mockMvc.perform(get("/api/weather/logs/" + latestLog.getId()).header("Authorization", bearer(userToken)))
			.andExpect(status().isForbidden());
	}

	private Map<String, String> loginPayload(String username, String password) {
		Map<String, String> payload = new HashMap<String, String>();
		payload.put("username", username);
		payload.put("password", password);
		return payload;
	}

	private String obtainToken(String username, String password) throws Exception {
		MvcResult result = this.mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(loginPayload(username, password))))
			.andExpect(status().isOk())
			.andReturn();

		String body = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		return this.objectMapper.readTree(body).get("token").asText();
	}

	private String bearer(String token) {
		return "Bearer " + token;
	}
}
