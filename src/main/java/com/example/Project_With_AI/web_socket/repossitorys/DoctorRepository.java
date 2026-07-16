package com.example.Project_With_AI.web_socket.repossitorys;


import java.util.List;
import java.util.Optional;

import com.example.Project_With_AI.web_socket.entitys.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUser_Id(Long userId);
    List<Doctor> findByIdIn(List<Long> ids);
    List<Doctor> findByUser_IdIn(List<Long> userIds);
}
