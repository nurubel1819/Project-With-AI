package com.example.Project_With_AI.hospital.repository;

import com.example.Project_With_AI.hospital.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
