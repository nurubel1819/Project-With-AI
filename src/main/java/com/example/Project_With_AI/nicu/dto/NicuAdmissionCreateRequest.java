package com.example.Project_With_AI.nicu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "Request payload to admit a child to a NICU bed")
public record NicuAdmissionCreateRequest(
	@Schema(description = "Child name", example = "Baby Noor")
	@NotBlank(message = "Child name is required")
	@Size(max = 120, message = "Child name must not exceed 120 characters")
	String childName,

	@Schema(description = "Guardian name", example = "Rafiqul Islam")
	@Size(max = 120, message = "Guardian name must not exceed 120 characters")
	String guardianName,

	@Schema(description = "Date of birth", example = "2026-04-10")
	@NotNull(message = "Date of birth is required")
	@PastOrPresent(message = "Date of birth cannot be in the future")
	LocalDate dateOfBirth,

	@Schema(description = "Clinical diagnosis", example = "Neonatal jaundice")
	@Size(max = 255, message = "Diagnosis must not exceed 255 characters")
	String diagnosis,

	@Schema(description = "Additional admission notes", example = "Monitor bilirubin every 6 hours")
	@Size(max = 500, message = "Notes must not exceed 500 characters")
	String notes
) {
}
