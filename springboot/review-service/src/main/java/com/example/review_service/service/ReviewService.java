package com.example.review_service.service;

import org.springframework.data.domain.Pageable;

import com.example.review_service.dto.CustomPageDTO;
import com.example.review_service.dto.review.ReviewRequestDTO;
import com.example.review_service.dto.review.ReviewResponseDTO;
import com.example.review_service.dto.review.ReviewUpdateDTO;


public interface ReviewService {
    
    public CustomPageDTO<ReviewResponseDTO> getAllReviews(Pageable Pageable);
    public ReviewResponseDTO addReview(ReviewRequestDTO request, Long userId);
    public ReviewResponseDTO updateReview(Long id, ReviewUpdateDTO request);
}
