package com.example.Project_With_AI.web_socket.services;


import com.example.Project_With_AI.auth.entity.Role;
import com.example.Project_With_AI.auth.entity.User;
import com.example.Project_With_AI.auth.repository.UserRepository;
import com.example.Project_With_AI.web_socket.dtos.DoctorRegisterRequest;
import com.example.Project_With_AI.web_socket.entitys.Doctor;
import com.example.Project_With_AI.web_socket.repossitorys.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DoctorRegisterService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Doctor register(DoctorRegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.DOCTOR)
                .build();
        User savedUser = userRepository.save(user);

        Doctor doctor = Doctor.builder()
                .user(savedUser)
                .specialization(request.specialization())
                .qualification(request.qualification())
                .licenseNumber(request.licenseNumber())
                .build();

        return doctorRepository.save(doctor);
    }
}
