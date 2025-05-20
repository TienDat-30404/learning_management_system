package com.example.review_service.mapper;

import org.springframework.stereotype.Component;

import com.example.review_service.dto.review.ReviewRequestDTO;
import com.example.review_service.dto.review.ReviewResponseDTO;
import com.example.review_service.model.Review;

@Component
public class ReviewMapper {
    public ReviewResponseDTO toDTO(Review review) {
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setId(review.getId());
        dto.setComment(review.getComment());
        dto.setRating(review.getRating());
        dto.setTargetType(review.getTargetType());
        dto.setTargetId(review.getTargetId());

        return dto;
    }

    public Review toEntity(ReviewRequestDTO request) {
        Review review = new Review();
        review.setComment(request.getComment());
        review.setRating(request.getRating());
        review.setUserId(request.getUserId());
        review.setTargetType(request.getTargetType());
        review.setTargetId(request.getTargetId());
        return review;
    }
}
