package com.example.Project_With_AI.hospital.service;

import com.example.Project_With_AI.hospital.dto.HospitalCreateRequest;
import com.example.Project_With_AI.hospital.dto.HospitalPatchRequest;
import com.example.Project_With_AI.hospital.dto.HospitalResponse;

public interface HospitalService {

	HospitalResponse createHospital(HospitalCreateRequest request);

	HospitalResponse patchHospital(Long id, HospitalPatchRequest request);
}
