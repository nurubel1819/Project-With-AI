package com.example.Project_With_AI.web_socket.controllers;


import com.example.Project_With_AI.web_socket.dtos.DoctorRegisterRequest;
import com.example.Project_With_AI.web_socket.entitys.Doctor;
import com.example.Project_With_AI.web_socket.services.DoctorRegisterService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/doctors")
@RequiredArgsConstructor
@SecurityRequirements
public class DoctorRegisterController {

    private final DoctorRegisterService doctorRegisterService;

    @PostMapping("/register")
    public ResponseEntity<Doctor> register(@Valid @RequestBody DoctorRegisterRequest request) {
        Doctor doctor = doctorRegisterService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(doctor);
    }
}
