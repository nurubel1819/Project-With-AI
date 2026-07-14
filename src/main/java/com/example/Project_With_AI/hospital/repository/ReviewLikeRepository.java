package com.example.Project_With_AI.hospital.repository;

import com.example.Project_With_AI.hospital.entity.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike,Long> {
    boolean existsByReviewIdAndUserId(Long reviewId, Long userId);
    void deleteByReviewIdAndUserId(Long reviewId, Long userId);
    long countByReviewId(Long reviewId);
}
