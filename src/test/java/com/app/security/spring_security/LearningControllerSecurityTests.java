package com.app.security.spring_security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class LearningControllerSecurityTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void publicEndpointShouldBeAccessibleWithoutLogin() throws Exception {
		mockMvc.perform(get("/api/public/hello"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("This is a public endpoint"));
	}

	@Test
	void protectedEndpointShouldRequireAuthentication() throws Exception {
		mockMvc.perform(get("/api/user/profile"))
			.andExpect(status().isUnauthorized());
	}

	@Test
	void protectedEndpointShouldBeAccessibleWithValidBasicAuth() throws Exception {
		mockMvc.perform(get("/api/user/profile").with(httpBasic("appuser", "password")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.username").value("appuser"));
	}

	@Test
	void userEndpointShouldAllowUserRole() throws Exception {
		mockMvc.perform(get("/api/user/dashboard").with(httpBasic("appuser", "password")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.requiredRole").value("ROLE_USER"));
	}

	@Test
	void userEndpointShouldRejectAdminRoleWithoutHierarchy() throws Exception {
		mockMvc.perform(get("/api/user/dashboard").with(httpBasic("adminuser", "admin123")))
			.andExpect(status().isForbidden());
	}

	@Test
	void adminEndpointShouldRejectUserRole() throws Exception {
		mockMvc.perform(get("/api/admin/dashboard").with(httpBasic("appuser", "password")))
			.andExpect(status().isForbidden());
	}

	@Test
	void adminEndpointShouldAllowAdminRole() throws Exception {
		mockMvc.perform(get("/api/admin/dashboard").with(httpBasic("adminuser", "admin123")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.requiredRole").value("ROLE_ADMIN"));
	}
}
