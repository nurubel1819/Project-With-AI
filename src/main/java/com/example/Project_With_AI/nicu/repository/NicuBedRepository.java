package com.example.Project_With_AI.nicu.repository;

import com.example.Project_With_AI.nicu.entity.NicuBed;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NicuBedRepository extends JpaRepository<NicuBed, Long> {

	List<NicuBed> findAllByHospitalIdOrderByIdAsc(Long hospitalId);

	Optional<NicuBed> findByIdAndHospitalId(Long id, Long hospitalId);

	boolean existsByHospitalIdAndBedCodeIgnoreCase(Long hospitalId, String bedCode);

	boolean existsByHospitalIdAndBedCodeIgnoreCaseAndIdNot(Long hospitalId, String bedCode, Long id);

	boolean existsByQrCode(String qrCode);

	boolean existsByQrCodeAndIdNot(String qrCode, Long id);
}
