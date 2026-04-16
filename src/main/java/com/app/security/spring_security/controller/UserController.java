package com.app.security.spring_security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.app.security.spring_security.dto.CreateUserRequest;
import com.app.security.spring_security.dto.RegisterUserRequest;
import com.app.security.spring_security.dto.UserResponse;
import com.app.security.spring_security.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterUserRequest request) {
		try {
			UserResponse response = this.userService.registerUser(request);
			return ResponseEntity.ok(response);
		}
		catch (IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

//	@GetMapping("/test")
//	public String hello() {
//		return "test API";
//	}

	@PreAuthorize("hasAuthority('USER_CREATE')")
	@PostMapping("/admin/create")
	public ResponseEntity<?> createUserByAdmin(@RequestBody CreateUserRequest request) {
		try {
			UserResponse response = this.userService.createUser(request);
			return ResponseEntity.ok(response);
		}
		catch (IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
}
