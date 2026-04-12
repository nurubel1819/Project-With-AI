package com.example.Project_With_AI.nicu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "NICU bed admission response payload")
public record NicuAdmissionResponse(
	Long id,
	Long nicuBedId,
	String childName,
	String guardianName,
	LocalDate dateOfBirth,
	String diagnosis,
	String notes,
	LocalDateTime admittedAt,
	LocalDateTime dischargedAt,
	boolean active,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
}
