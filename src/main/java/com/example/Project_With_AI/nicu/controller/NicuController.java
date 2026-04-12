package com.example.Project_With_AI.nicu.controller;

import com.example.Project_With_AI.nicu.dto.NicuAdmissionCreateRequest;
import com.example.Project_With_AI.nicu.dto.NicuAdmissionResponse;
import com.example.Project_With_AI.nicu.dto.NicuBedCreateRequest;
import com.example.Project_With_AI.nicu.dto.NicuBedPatchRequest;
import com.example.Project_With_AI.nicu.dto.NicuBedResponse;
import com.example.Project_With_AI.nicu.service.NicuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hospitals/{hospitalId}/nicu-beds")
@RequiredArgsConstructor
@Tag(name = "NICU", description = "NICU bed and child admission management APIs")
public class NicuController {

	private final NicuService nicuService;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Create NICU bed", description = "Creates a NICU bed under a hospital")
	public ResponseEntity<NicuBedResponse> createBed(
		@PathVariable Long hospitalId,
		@Valid @RequestBody NicuBedCreateRequest request
	) {
		return ResponseEntity.status(HttpStatus.CREATED).body(nicuService.createBed(hospitalId, request));
	}

	@GetMapping
	@Operation(summary = "List NICU beds", description = "Returns all NICU beds for a hospital")
	public ResponseEntity<List<NicuBedResponse>> getBeds(@PathVariable Long hospitalId) {
		return ResponseEntity.ok(nicuService.getBedsByHospital(hospitalId));
	}

	@PatchMapping("/{bedId}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Patch NICU bed", description = "Updates NICU bed metadata (bed code, QR code, extra information)")
	public ResponseEntity<NicuBedResponse> patchBed(
		@PathVariable Long hospitalId,
		@PathVariable Long bedId,
		@Valid @RequestBody NicuBedPatchRequest request
	) {
		return ResponseEntity.ok(nicuService.patchBed(hospitalId, bedId, request));
	}

	@PostMapping("/{bedId}/admissions")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Admit child", description = "Admits a child to a NICU bed. Bed must be unoccupied.")
	public ResponseEntity<NicuAdmissionResponse> admitChild(
		@PathVariable Long hospitalId,
		@PathVariable Long bedId,
		@Valid @RequestBody NicuAdmissionCreateRequest request
	) {
		return ResponseEntity.status(HttpStatus.CREATED).body(nicuService.admitChild(hospitalId, bedId, request));
	}

	@PostMapping("/{bedId}/admissions/{admissionId}/discharge")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Discharge child", description = "Discharges a child from a specific NICU admission")
	public ResponseEntity<NicuAdmissionResponse> dischargeChild(
		@PathVariable Long hospitalId,
		@PathVariable Long bedId,
		@PathVariable Long admissionId
	) {
		return ResponseEntity.ok(nicuService.dischargeChild(hospitalId, bedId, admissionId));
	}

	@GetMapping("/{bedId}/admissions")
	@Operation(summary = "List bed admissions", description = "Returns admission history for a NICU bed")
	public ResponseEntity<List<NicuAdmissionResponse>> getBedAdmissions(
		@PathVariable Long hospitalId,
		@PathVariable Long bedId
	) {
		return ResponseEntity.ok(nicuService.getBedAdmissions(hospitalId, bedId));
	}
}
