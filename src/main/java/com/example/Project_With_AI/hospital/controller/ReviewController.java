package com.example.Project_With_AI.hospital.controller;

import com.example.Project_With_AI.hospital.dto.HospitalReviewDTO;
import com.example.Project_With_AI.hospital.service.ReviewLikeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews/")
@RequiredArgsConstructor
@Tag(name = "Hospital Review", description = "Hospital Review management APIs")
public class ReviewController {

    private final ReviewLikeService reviewLikeService;

    @PostMapping("like")
    public ResponseEntity<Boolean> setLike(
            @RequestParam Long reviewId,
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(reviewLikeService.toggleLike(reviewId,userId));
    }

    @PostMapping("/create")
    public ResponseEntity<HospitalReviewDTO> createHospitalReview(@RequestBody @Valid HospitalReviewDTO dto) {
        return ResponseEntity.ok(reviewLikeService.createReview(dto));
    }

    @GetMapping("all/review")
    public ResponseEntity<List<HospitalReviewDTO>> getAllReviews() {
        List<HospitalReviewDTO> reviews = reviewLikeService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }
}
