package com.example.Project_With_AI.common.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiErrorResponse(
	LocalDateTime timestamp,
	int status,
	String message,
	Map<String, String> errors
) {
}
