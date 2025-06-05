package com.example.review_service.dto.review;

import com.example.review_service.dto.user.UserResponseDTO;
import com.example.review_service.enums.TargetType;

import lombok.Data;

@Data
public class ReviewResponseDTO {
    private Long id; 
    private String comment;
    private Long rating;
    private TargetType targetType;
    private Long targetId;
    private UserResponseDTO user;
}
