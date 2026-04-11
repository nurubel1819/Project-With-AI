package com.example.Project_With_AI.hospital.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for creating a hospital")
public record HospitalCreateRequest(

	@Schema(description = "Hospital name", example = "Square Hospital")
	@NotBlank(message = "Hospital name is required")
	@Size(max = 150, message = "Hospital name must not exceed 150 characters")
	String name,

	@Schema(description = "Full hospital address", example = "18/F Bir Uttam Qazi Nuruzzaman Sarak, Dhaka")
	@NotBlank(message = "Address is required")
	@Size(max = 255, message = "Address must not exceed 255 characters")
	String address,

	@Schema(description = "Latitude coordinate", example = "23.7516")
	@NotNull(message = "Latitude is required")
	@DecimalMin(value = "-90.0", message = "Latitude must be greater than or equal to -90")
	@DecimalMax(value = "90.0", message = "Latitude must be less than or equal to 90")
	Double latitude,

	@Schema(description = "Longitude coordinate", example = "90.3932")
	@NotNull(message = "Longitude is required")
	@DecimalMin(value = "-180.0", message = "Longitude must be greater than or equal to -180")
	@DecimalMax(value = "180.0", message = "Longitude must be less than or equal to 180")
	Double longitude,

	@Schema(description = "Hospital contact phone number", example = "+8801712345678")
	@Pattern(
		regexp = "^[+]?[0-9()\\-\\s]{7,30}$",
		message = "Phone number format is invalid"
	)
	String phoneNumber,

	@Schema(description = "Hospital contact email", example = "info@squarehospital.com")
	@Email(message = "Email format is invalid")
	@Size(max = 120, message = "Email must not exceed 120 characters")
	String email,

	@Schema(description = "Additional hospital information", example = "24/7 emergency service available")
	@Size(max = 500, message = "Description must not exceed 500 characters")
	String description
) {
}
