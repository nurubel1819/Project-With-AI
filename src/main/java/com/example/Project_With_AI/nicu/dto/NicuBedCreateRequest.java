package com.example.Project_With_AI.nicu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for creating a NICU bed")
public record NicuBedCreateRequest(
	@Schema(description = "Bed code unique within a hospital", example = "NICU-BED-01")
	@NotBlank(message = "Bed code is required")
	@Size(max = 50, message = "Bed code must not exceed 50 characters")
	String bedCode,

	@Schema(description = "Unique QR code content for this bed", example = "QR-NICU-00001")
	@NotBlank(message = "QR code is required")
	@Size(max = 255, message = "QR code must not exceed 255 characters")
	String qrCode,

	@Schema(description = "Additional information for this bed", example = "Near nurse station")
	@Size(max = 500, message = "Extra information must not exceed 500 characters")
	String extraInformation
) {
}
