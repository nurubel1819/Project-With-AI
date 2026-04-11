package com.example.Project_With_AI.hospital.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Hospital response payload")
public record HospitalResponse(
	Long id,
	String name,
	String address,
	Double latitude,
	Double longitude,
	String phoneNumber,
	String email,
	String description,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
}
