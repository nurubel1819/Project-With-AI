package com.example.Project_With_AI.hospital.repository;

import com.example.Project_With_AI.hospital.dto.HospitalReviewDTO;
import com.example.Project_With_AI.hospital.entity.HospitalReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HospitalReviewRepository extends JpaRepository<HospitalReview,Long> {
    @Query("""
        SELECT new com.example.Project_With_AI.hospital.dto.HospitalReviewDTO(
            r.id,
            r.user.id,
            r.rating,
            r.message,
            CAST(COUNT(l.id) AS integer)
        )
        FROM HospitalReview r
        LEFT JOIN ReviewLike l ON l.review.id = r.id
        GROUP BY r.id, r.user.id, r.rating, r.message
        ORDER BY r.createdAt DESC
        """)
    List<HospitalReviewDTO> findAllReviewsWithLikeCount();
}
