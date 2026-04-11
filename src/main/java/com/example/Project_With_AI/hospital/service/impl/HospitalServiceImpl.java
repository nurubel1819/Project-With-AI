package com.example.Project_With_AI.hospital.service.impl;

import com.example.Project_With_AI.common.exception.ResourceNotFoundException;
import com.example.Project_With_AI.hospital.dto.HospitalCreateRequest;
import com.example.Project_With_AI.hospital.dto.HospitalPatchRequest;
import com.example.Project_With_AI.hospital.dto.HospitalResponse;
import com.example.Project_With_AI.hospital.entity.Hospital;
import com.example.Project_With_AI.hospital.repository.HospitalRepository;
import com.example.Project_With_AI.hospital.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

	private final HospitalRepository hospitalRepository;

	@Override
	@Transactional
	public HospitalResponse createHospital(HospitalCreateRequest request) {
		Hospital hospital = Hospital.builder()
			.name(request.name())
			.address(request.address())
			.latitude(request.latitude())
			.longitude(request.longitude())
			.phoneNumber(normalizeBangladeshiPhoneNumber(request.phoneNumber()))
			.email(request.email())
			.description(request.description())
			.build();

		Hospital savedHospital = hospitalRepository.save(hospital);
		return mapToResponse(savedHospital);
	}

	@Override
	@Transactional
	public HospitalResponse patchHospital(Long id, HospitalPatchRequest request) {
		Hospital hospital = hospitalRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Hospital not found with id: " + id));

		if (request.name() != null) {
			hospital.setName(request.name());
		}
		if (request.address() != null) {
			hospital.setAddress(request.address());
		}
		if (request.latitude() != null) {
			hospital.setLatitude(request.latitude());
		}
		if (request.longitude() != null) {
			hospital.setLongitude(request.longitude());
		}
		if (request.phoneNumber() != null) {
			hospital.setPhoneNumber(normalizeBangladeshiPhoneNumber(request.phoneNumber()));
		}
		if (request.email() != null) {
			hospital.setEmail(request.email());
		}
		if (request.description() != null) {
			hospital.setDescription(request.description());
		}

		Hospital updatedHospital = hospitalRepository.save(hospital);
		return mapToResponse(updatedHospital);
	}

	private HospitalResponse mapToResponse(Hospital hospital) {
		return new HospitalResponse(
			hospital.getId(),
			hospital.getName(),
			hospital.getAddress(),
			hospital.getLatitude(),
			hospital.getLongitude(),
			hospital.getPhoneNumber(),
			hospital.getEmail(),
			hospital.getDescription(),
			hospital.getCreatedAt(),
			hospital.getUpdatedAt()
		);
	}

	private String normalizeBangladeshiPhoneNumber(String phoneNumber) {
		if (phoneNumber == null || phoneNumber.isBlank()) {
			return phoneNumber;
		}

		if (phoneNumber.startsWith("+88")) {
			return phoneNumber;
		}

		return "+88" + phoneNumber;
	}
}
