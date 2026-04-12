package com.example.Project_With_AI.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
	@NotBlank(message = "Email is required")
	@Email(message = "Email must be valid")
	String email,

	@NotBlank(message = "Password is required")
	String password
) {
}
