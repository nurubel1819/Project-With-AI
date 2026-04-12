package com.example.Project_With_AI.auth.service;

import com.example.Project_With_AI.auth.dto.AuthResponse;
import com.example.Project_With_AI.auth.dto.LoginRequest;
import com.example.Project_With_AI.auth.dto.RegisterRequest;

public interface AuthService {
	AuthResponse register(RegisterRequest request);

	AuthResponse login(LoginRequest request);
}
