package com.example.Project_With_AI.auth.dto;

import com.example.Project_With_AI.auth.entity.Role;

public record AuthResponse(
	String accessToken,
	String tokenType,
	long expiresIn,
	String email,
	Role role
) {
}
