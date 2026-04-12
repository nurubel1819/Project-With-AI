package com.example.Project_With_AI.nicu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for partially updating NICU bed metadata")
public record NicuBedPatchRequest(
	@Schema(description = "Bed code unique within a hospital", example = "NICU-BED-01")
	@Size(max = 50, message = "Bed code must not exceed 50 characters")
	String bedCode,

	@Schema(description = "Unique QR code content for this bed", example = "QR-NICU-00001")
	@Size(max = 255, message = "QR code must not exceed 255 characters")
	String qrCode,

	@Schema(description = "Additional information for this bed", example = "Needs oxygen line maintenance")
	@Size(max = 500, message = "Extra information must not exceed 500 characters")
	String extraInformation
) {
}
