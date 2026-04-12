package com.example.Project_With_AI.nicu.service.impl;

import com.example.Project_With_AI.common.exception.ResourceNotFoundException;
import com.example.Project_With_AI.hospital.entity.Hospital;
import com.example.Project_With_AI.hospital.repository.HospitalRepository;
import com.example.Project_With_AI.nicu.dto.NicuAdmissionCreateRequest;
import com.example.Project_With_AI.nicu.dto.NicuAdmissionResponse;
import com.example.Project_With_AI.nicu.dto.NicuBedCreateRequest;
import com.example.Project_With_AI.nicu.dto.NicuBedPatchRequest;
import com.example.Project_With_AI.nicu.dto.NicuBedResponse;
import com.example.Project_With_AI.nicu.entity.NicuAdmission;
import com.example.Project_With_AI.nicu.entity.NicuBed;
import com.example.Project_With_AI.nicu.repository.NicuAdmissionRepository;
import com.example.Project_With_AI.nicu.repository.NicuBedRepository;
import com.example.Project_With_AI.nicu.service.NicuService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NicuServiceImpl implements NicuService {

	private final HospitalRepository hospitalRepository;
	private final NicuBedRepository nicuBedRepository;
	private final NicuAdmissionRepository nicuAdmissionRepository;

	@Override
	@Transactional
	public NicuBedResponse createBed(Long hospitalId, NicuBedCreateRequest request) {
		Hospital hospital = getHospitalOrThrow(hospitalId);

		if (nicuBedRepository.existsByHospitalIdAndBedCodeIgnoreCase(hospitalId, request.bedCode().trim())) {
			throw new IllegalArgumentException("Bed code already exists in this hospital");
		}
		if (nicuBedRepository.existsByQrCode(request.qrCode().trim())) {
			throw new IllegalArgumentException("QR code already exists");
		}

		NicuBed nicuBed = NicuBed.builder()
			.hospital(hospital)
			.bedCode(request.bedCode().trim())
			.qrCode(request.qrCode().trim())
			.occupied(false)
			.extraInformation(request.extraInformation())
			.build();

		return mapBedToResponse(nicuBedRepository.save(nicuBed), null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<NicuBedResponse> getBedsByHospital(Long hospitalId) {
		getHospitalOrThrow(hospitalId);
		return nicuBedRepository.findAllByHospitalIdOrderByIdAsc(hospitalId)
			.stream()
			.map(this::mapBedWithActiveAdmission)
			.toList();
	}

	@Override
	@Transactional
	public NicuBedResponse patchBed(Long hospitalId, Long bedId, NicuBedPatchRequest request) {
		getHospitalOrThrow(hospitalId);
		NicuBed nicuBed = getBedOrThrow(hospitalId, bedId);

		if (request.bedCode() != null) {
			String bedCode = request.bedCode().trim();
			if (bedCode.isBlank()) {
				throw new IllegalArgumentException("Bed code cannot be blank");
			}
			if (nicuBedRepository.existsByHospitalIdAndBedCodeIgnoreCaseAndIdNot(hospitalId, bedCode, bedId)) {
				throw new IllegalArgumentException("Bed code already exists in this hospital");
			}
			nicuBed.setBedCode(bedCode);
		}

		if (request.qrCode() != null) {
			String qrCode = request.qrCode().trim();
			if (qrCode.isBlank()) {
				throw new IllegalArgumentException("QR code cannot be blank");
			}
			if (nicuBedRepository.existsByQrCodeAndIdNot(qrCode, bedId)) {
				throw new IllegalArgumentException("QR code already exists");
			}
			nicuBed.setQrCode(qrCode);
		}

		if (request.extraInformation() != null) {
			nicuBed.setExtraInformation(request.extraInformation());
		}

		NicuBed savedBed = nicuBedRepository.save(nicuBed);
		return mapBedWithActiveAdmission(savedBed);
	}

	@Override
	@Transactional
	public NicuAdmissionResponse admitChild(Long hospitalId, Long bedId, NicuAdmissionCreateRequest request) {
		getHospitalOrThrow(hospitalId);
		NicuBed nicuBed = getBedOrThrow(hospitalId, bedId);

		if (nicuBed.isOccupied() || nicuAdmissionRepository.existsByNicuBedIdAndDischargedAtIsNull(bedId)) {
			throw new IllegalArgumentException("NICU bed is already occupied");
		}

		NicuAdmission admission = NicuAdmission.builder()
			.nicuBed(nicuBed)
			.childName(request.childName().trim())
			.guardianName(request.guardianName())
			.dateOfBirth(request.dateOfBirth())
			.diagnosis(request.diagnosis())
			.notes(request.notes())
			.admittedAt(LocalDateTime.now())
			.build();

		nicuBed.setOccupied(true);
		nicuBedRepository.save(nicuBed);

		return mapAdmissionToResponse(nicuAdmissionRepository.save(admission));
	}

	@Override
	@Transactional
	public NicuAdmissionResponse dischargeChild(Long hospitalId, Long bedId, Long admissionId) {
		getHospitalOrThrow(hospitalId);
		NicuBed nicuBed = getBedOrThrow(hospitalId, bedId);

		NicuAdmission admission = nicuAdmissionRepository
			.findByIdAndNicuBedIdAndNicuBedHospitalId(admissionId, bedId, hospitalId)
			.orElseThrow(() -> new ResourceNotFoundException("NICU admission not found with id: " + admissionId));

		if (admission.getDischargedAt() != null) {
			throw new IllegalArgumentException("Child is already discharged from this admission");
		}

		admission.setDischargedAt(LocalDateTime.now());
		nicuBed.setOccupied(false);
		nicuBedRepository.save(nicuBed);

		NicuAdmission savedAdmission = nicuAdmissionRepository.save(admission);
		return mapAdmissionToResponse(savedAdmission);
	}

	@Override
	@Transactional(readOnly = true)
	public List<NicuAdmissionResponse> getBedAdmissions(Long hospitalId, Long bedId) {
		getHospitalOrThrow(hospitalId);
		getBedOrThrow(hospitalId, bedId);
		return nicuAdmissionRepository.findAllByNicuBedIdOrderByAdmittedAtDesc(bedId)
			.stream()
			.map(this::mapAdmissionToResponse)
			.toList();
	}

	private Hospital getHospitalOrThrow(Long hospitalId) {
		return hospitalRepository.findById(hospitalId)
			.orElseThrow(() -> new ResourceNotFoundException("Hospital not found with id: " + hospitalId));
	}

	private NicuBed getBedOrThrow(Long hospitalId, Long bedId) {
		return nicuBedRepository.findByIdAndHospitalId(bedId, hospitalId)
			.orElseThrow(() -> new ResourceNotFoundException("NICU bed not found with id: " + bedId));
	}

	private NicuBedResponse mapBedWithActiveAdmission(NicuBed nicuBed) {
		NicuAdmissionResponse activeAdmission = nicuAdmissionRepository
			.findFirstByNicuBedIdAndDischargedAtIsNullOrderByAdmittedAtDesc(nicuBed.getId())
			.map(this::mapAdmissionToResponse)
			.orElse(null);
		return mapBedToResponse(nicuBed, activeAdmission);
	}

	private NicuBedResponse mapBedToResponse(NicuBed nicuBed, NicuAdmissionResponse activeAdmission) {
		return new NicuBedResponse(
			nicuBed.getId(),
			nicuBed.getHospital().getId(),
			nicuBed.getBedCode(),
			nicuBed.getQrCode(),
			nicuBed.isOccupied(),
			nicuBed.getExtraInformation(),
			activeAdmission,
			nicuBed.getCreatedAt(),
			nicuBed.getUpdatedAt()
		);
	}

	private NicuAdmissionResponse mapAdmissionToResponse(NicuAdmission admission) {
		return new NicuAdmissionResponse(
			admission.getId(),
			admission.getNicuBed().getId(),
			admission.getChildName(),
			admission.getGuardianName(),
			admission.getDateOfBirth(),
			admission.getDiagnosis(),
			admission.getNotes(),
			admission.getAdmittedAt(),
			admission.getDischargedAt(),
			admission.getDischargedAt() == null,
			admission.getCreatedAt(),
			admission.getUpdatedAt()
		);
	}
}
