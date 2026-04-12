package com.example.Project_With_AI.nicu.service;

import com.example.Project_With_AI.nicu.dto.NicuAdmissionCreateRequest;
import com.example.Project_With_AI.nicu.dto.NicuAdmissionResponse;
import com.example.Project_With_AI.nicu.dto.NicuBedCreateRequest;
import com.example.Project_With_AI.nicu.dto.NicuBedPatchRequest;
import com.example.Project_With_AI.nicu.dto.NicuBedResponse;
import java.util.List;

public interface NicuService {

	NicuBedResponse createBed(Long hospitalId, NicuBedCreateRequest request);

	List<NicuBedResponse> getBedsByHospital(Long hospitalId);

	NicuBedResponse patchBed(Long hospitalId, Long bedId, NicuBedPatchRequest request);

	NicuAdmissionResponse admitChild(Long hospitalId, Long bedId, NicuAdmissionCreateRequest request);

	NicuAdmissionResponse dischargeChild(Long hospitalId, Long bedId, Long admissionId);

	List<NicuAdmissionResponse> getBedAdmissions(Long hospitalId, Long bedId);
}
