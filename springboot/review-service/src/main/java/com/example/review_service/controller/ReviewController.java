package com.example.review_service.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.review_service.dto.ApiResponseDTO;
import com.example.review_service.dto.CustomPageDTO;
import com.example.review_service.dto.review.ReviewRequestDTO;
import com.example.review_service.dto.review.ReviewResponseDTO;
import com.example.review_service.dto.review.ReviewUpdateDTO;
import com.example.review_service.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Controller
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<CustomPageDTO<ReviewResponseDTO>>> getAllReviews(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        CustomPageDTO<ReviewResponseDTO> reviews = reviewService.getAllReviews(pageable);
        ApiResponseDTO<CustomPageDTO<ReviewResponseDTO>> resposne = new ApiResponseDTO<>(
                200, reviews, "List reviews");
        return ResponseEntity.ok(resposne);
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<ReviewResponseDTO>> addReview(@Valid @RequestBody ReviewRequestDTO request) {
        ReviewResponseDTO review = reviewService.addReview(request);
        ApiResponseDTO<ReviewResponseDTO> response = new ApiResponseDTO<>(
                201, review, "Add Review successful");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ReviewResponseDTO>> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewUpdateDTO request) {
        ReviewResponseDTO review = reviewService.updateReview(id, request);
        ApiResponseDTO<ReviewResponseDTO> response = new ApiResponseDTO<>(
                200, review, "Update Review Successful");
        return ResponseEntity.ok(response);
    }
}
