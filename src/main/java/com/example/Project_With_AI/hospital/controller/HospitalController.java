package com.example.Project_With_AI.hospital.controller;

import com.example.Project_With_AI.hospital.dto.HospitalCreateRequest;
import com.example.Project_With_AI.hospital.dto.HospitalPatchRequest;
import com.example.Project_With_AI.hospital.dto.HospitalResponse;
import com.example.Project_With_AI.hospital.service.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hospitals")
@RequiredArgsConstructor
@Tag(name = "Hospital", description = "Hospital management APIs")
public class HospitalController {

	private final HospitalService hospitalService;

	@PostMapping
	@Operation(summary = "Create hospital", description = "Creates a new hospital with location and contact information")
	@ApiResponses({
		@ApiResponse(
			responseCode = "201",
			description = "Hospital created successfully",
			content = @Content(schema = @Schema(implementation = HospitalResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "Invalid request data",
			content = @Content(
				mediaType = "application/json",
				examples = @ExampleObject(
					value = "{\"message\":\"Validation failed\",\"errors\":{\"name\":\"Hospital name is required\"}}"
				)
			)
		)
	})
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HospitalResponse> createHospital(@Valid @RequestBody HospitalCreateRequest request) {
		HospitalResponse response = hospitalService.createHospital(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PatchMapping("/{id}")
	@Operation(summary = "Patch hospital", description = "Partially updates an existing hospital. Only provided fields are updated.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "Hospital updated successfully",
			content = @Content(schema = @Schema(implementation = HospitalResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "Invalid request data",
			content = @Content(
				mediaType = "application/json",
				examples = @ExampleObject(
					value = "{\"message\":\"Validation failed\",\"errors\":{\"phoneNumber\":\"Phone number must be a valid Bangladeshi mobile number\"}}"
				)
			)
		),
		@ApiResponse(
			responseCode = "404",
			description = "Hospital not found",
			content = @Content(
				mediaType = "application/json",
				examples = @ExampleObject(
					value = "{\"message\":\"Hospital not found with id: 1\"}"
				)
			)
		)
	})
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HospitalResponse> patchHospital(
		@PathVariable Long id,
		@Valid @RequestBody HospitalPatchRequest request
	) {
		HospitalResponse response = hospitalService.patchHospital(id, request);
		return ResponseEntity.ok(response);
	}
}
