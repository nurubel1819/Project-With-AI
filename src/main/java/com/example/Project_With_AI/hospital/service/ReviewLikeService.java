package com.example.Project_With_AI.hospital.service;

import com.example.Project_With_AI.auth.entity.User;
import com.example.Project_With_AI.auth.repository.UserRepository;
import com.example.Project_With_AI.common.exception.ResourceNotFoundException;
import com.example.Project_With_AI.hospital.dto.HospitalReviewDTO;
import com.example.Project_With_AI.hospital.entity.HospitalReview;
import com.example.Project_With_AI.hospital.entity.ReviewLike;
import com.example.Project_With_AI.hospital.mapper.HospitalReviewMap;
import com.example.Project_With_AI.hospital.repository.HospitalReviewRepository;
import com.example.Project_With_AI.hospital.repository.ReviewLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewLikeService {

    private final ReviewLikeRepository reviewLikeRepository;
    private final HospitalReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final HospitalReviewMap hospitalReviewMap;

    @Transactional
    public boolean toggleLike(Long reviewId, Long userId) {
        boolean alreadyLiked = reviewLikeRepository
                .existsByReviewIdAndUserId(reviewId, userId);

        if (alreadyLiked) {
            reviewLikeRepository.deleteByReviewIdAndUserId(reviewId, userId);
            return false; // unliked
        } else {
            HospitalReview review = reviewRepository.getReferenceById(reviewId);
            User user = userRepository.getReferenceById(userId);

            ReviewLike like = ReviewLike.builder()
                    .review(review)
                    .user(user)
                    .build();
            reviewLikeRepository.save(like);
            return true; // liked
        }
    }

    @Transactional
    public HospitalReviewDTO createReview(HospitalReviewDTO dto) {
        if (!userRepository.existsById(dto.getUserId())) {
            throw new ResourceNotFoundException("User not found");
        }
        return hospitalReviewMap.map(reviewRepository.save(hospitalReviewMap.map(dto)));
    }

    public List<HospitalReviewDTO> getAllReviews() {
        return reviewRepository.findAllReviewsWithLikeCount();
    }
}
