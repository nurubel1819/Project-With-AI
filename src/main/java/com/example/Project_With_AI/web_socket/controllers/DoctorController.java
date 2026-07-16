package com.example.Project_With_AI.web_socket.controllers;


import com.example.Project_With_AI.web_socket.entitys.Doctor;
import com.example.Project_With_AI.web_socket.repossitorys.DoctorRepository;
import com.example.Project_With_AI.web_socket.services.DoctorPresenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorPresenceService presenceService;
    private final DoctorRepository doctorRepository;

    @GetMapping("/live")
    public List<Doctor> getLiveDoctors() {
        List<Long> onlineUserIds  = presenceService.getOnlineDoctorIds().stream().toList();
        return doctorRepository.findByUser_IdIn(onlineUserIds);
    }
}
