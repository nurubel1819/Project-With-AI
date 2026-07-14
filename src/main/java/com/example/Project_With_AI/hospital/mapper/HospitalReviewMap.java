package com.example.Project_With_AI.hospital.mapper;

import com.example.Project_With_AI.auth.repository.UserRepository;
import com.example.Project_With_AI.hospital.dto.HospitalReviewDTO;
import com.example.Project_With_AI.hospital.entity.HospitalReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HospitalReviewMap {

    private final UserRepository userRepository;


    public HospitalReview map(HospitalReviewDTO dto) {
        HospitalReview entity = new HospitalReview();
        entity.setUser(userRepository.getReferenceById(dto.getUserId()));
        entity.setRating(dto.getRating());
        entity.setMessage(dto.getMessage());

        return entity;
    }

    public HospitalReviewDTO map(HospitalReview entity) {
        HospitalReviewDTO dto = new HospitalReviewDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setRating(entity.getRating());
        dto.setMessage(entity.getMessage());

        return dto;
    }
}
