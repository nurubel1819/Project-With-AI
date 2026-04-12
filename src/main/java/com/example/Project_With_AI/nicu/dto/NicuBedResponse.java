package com.example.Project_With_AI.nicu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "NICU bed response payload")
public record NicuBedResponse(
	Long id,
	Long hospitalId,
	String bedCode,
	String qrCode,
	boolean occupied,
	String extraInformation,
	NicuAdmissionResponse activeAdmission,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
}
