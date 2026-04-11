package com.example.Project_With_AI.hospital.service.impl;

import com.example.Project_With_AI.hospital.dto.HospitalCreateRequest;
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
			.phoneNumber(request.phoneNumber())
			.email(request.email())
			.description(request.description())
			.build();

		Hospital savedHospital = hospitalRepository.save(hospital);
		return mapToResponse(savedHospital);
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
}
