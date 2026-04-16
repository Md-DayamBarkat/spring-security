package com.app.security.spring_security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.security.spring_security.dto.CreateUserRequest;
import com.app.security.spring_security.dto.RegisterUserRequest;
import com.app.security.spring_security.dto.UserResponse;
import com.app.security.spring_security.entity.AppUser;
import com.app.security.spring_security.enums.Role;
import com.app.security.spring_security.repository.AppUserRepository;

@Service
public class UserService {

	private final AppUserRepository appUserRepository;

	private final PasswordEncoder passwordEncoder;

	public UserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
		this.appUserRepository = appUserRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public UserResponse registerUser(RegisterUserRequest request) {
		return saveUser(request.getUsername(), request.getPassword(), Role.USER);
	}

	public UserResponse createUser(CreateUserRequest request) {
		return saveUser(request.getUsername(), request.getPassword(), Role.fromValue(request.getRole()));
	}

	public void seedUserIfMissing(String username, String password, Role role) {
		if (!this.appUserRepository.existsByUsername(username)) {
			saveUser(username, password, role);
		}
	}

	private UserResponse saveUser(String username, String password, Role role) {
		if (username == null || username.trim().isEmpty()) {
			throw new IllegalArgumentException("Username is required");
		}
		if (password == null || password.trim().isEmpty()) {
			throw new IllegalArgumentException("Password is required");
		}
		if (this.appUserRepository.existsByUsername(username)) {
			throw new IllegalArgumentException("User already exists: " + username);
		}

		AppUser savedUser = this.appUserRepository.save(
			new AppUser(username.trim(), this.passwordEncoder.encode(password), role)
		);

		return new UserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getRole().name());
	}
}
