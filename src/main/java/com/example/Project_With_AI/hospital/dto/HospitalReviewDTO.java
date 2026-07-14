package com.example.Project_With_AI.hospital.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalReviewDTO {

    public Long id;

    @NotNull(message = "Rating is required")
    public Long userId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    public Integer rating;

    @Size(max = 1000, message = "Message must not exceed 1000 characters")
    String message;

    public Integer likeCount;
}
