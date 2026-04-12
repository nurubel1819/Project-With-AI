package com.example.Project_With_AI.nicu.repository;

import com.example.Project_With_AI.nicu.entity.NicuAdmission;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NicuAdmissionRepository extends JpaRepository<NicuAdmission, Long> {

	boolean existsByNicuBedIdAndDischargedAtIsNull(Long nicuBedId);

	Optional<NicuAdmission> findFirstByNicuBedIdAndDischargedAtIsNullOrderByAdmittedAtDesc(Long nicuBedId);

	List<NicuAdmission> findAllByNicuBedIdOrderByAdmittedAtDesc(Long nicuBedId);

	Optional<NicuAdmission> findByIdAndNicuBedIdAndNicuBedHospitalId(Long id, Long nicuBedId, Long hospitalId);
}
