package com.app.security.spring_security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
@ConditionalOnProperty(prefix = "app.security.oauth2", name = "enabled", havingValue = "true")
public class OAuth2SecurityConfig {

	@Bean
	@Order(1)
	public SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.requestMatcher(new OrRequestMatcher(
				new AntPathRequestMatcher("/oauth-demo/**"),
				new AntPathRequestMatcher("/oauth2/**"),
				new AntPathRequestMatcher("/login/**")
			))
			.authorizeRequests()
				.antMatchers("/oauth-demo/public").permitAll()
				.anyRequest().authenticated()
			.and()
			.oauth2Login();

		return http.build();
	}
}
