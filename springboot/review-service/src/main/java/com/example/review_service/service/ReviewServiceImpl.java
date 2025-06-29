package com.example.review_service.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.review_service.client.UserClient;
import com.example.review_service.dto.ApiResponseDTO;
import com.example.review_service.dto.CustomPageDTO;
import com.example.review_service.dto.review.ReviewRequestDTO;
import com.example.review_service.dto.review.ReviewResponseDTO;
import com.example.review_service.dto.review.ReviewUpdateDTO;
import com.example.review_service.dto.user.UserResponseDTO;
import com.example.review_service.mapper.ReviewMapper;
import com.example.review_service.model.Review;
import com.example.review_service.repository.ReviewRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserClient userClient;
    private final ReviewMapper reviewMapper;
    
    @Value("${internal.api.key}")
    private String apiInternal;

    public Map<Long, UserResponseDTO> fetchUsers(List<Long> userIds) {
        try {
            ApiResponseDTO<List<UserResponseDTO>> response = userClient.getUserByIds(userIds);
            List<UserResponseDTO> users = response.getData();
            return users.stream()
                    .filter(user -> user != null && user.getId() != null)
                    .collect(Collectors.toMap(UserResponseDTO::getId, user -> user));
        } catch (Exception e) {
            log.error("Failed to fetch users: {}", e.getMessage());
            return Map.of();
        }

    }

    public CustomPageDTO<ReviewResponseDTO> getAllReviews(Pageable pageable) {
        Page<Review> pageReview = reviewRepository.findAll(pageable);
        List<Review> reviews = pageReview.getContent();
        List<Long> userIds = reviews.stream().map(Review::getUserId).collect(Collectors.toList());
        Map<Long, UserResponseDTO> users = fetchUsers(userIds);
        List<ReviewResponseDTO> listReviews = reviews.stream().map(review -> {
            ReviewResponseDTO dto = reviewMapper.toDTO(review);
            UserResponseDTO user = users.get(review.getUserId());
            if (user != null) {
                dto.setUser(user);
            }
            return dto;
        }).collect(Collectors.toList());

        return new CustomPageDTO<>(
                listReviews, pageReview.getTotalElements(), pageReview.getTotalPages());
    }

    public ReviewResponseDTO addReview(ReviewRequestDTO request, Long userId) {
        System.out.println("dataaa" + request);
        Boolean existUser = userClient.checkExistUser(userId, apiInternal);
        if (existUser == null || !existUser) {
            throw new EntityNotFoundException("User with id " + userId + " does not exist");
        }

        Review review = reviewMapper.toEntity(request);
        review.setUserId(userId);
        review = reviewRepository.save(review);

        ApiResponseDTO<UserResponseDTO> user = userClient.getUserById(userId);
        ReviewResponseDTO response = reviewMapper.toDTO(review);
        response.setUser(user.getData());
        return response;

    }

    public ReviewResponseDTO updateReview(Long id, ReviewUpdateDTO request) {
        Review review = reviewRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Review not found with id=" + id));
        if(request.getComment() != null) review.setComment(request.getComment());
        if(request.getRating() != null) review.setRating(request.getRating());
        Review updatedReview = reviewRepository.save(review);
        ApiResponseDTO<UserResponseDTO> userResponse = userClient.getUserById(updatedReview.getUserId());
        ReviewResponseDTO response = reviewMapper.toDTO(updatedReview);
        response.setUser(userResponse.getData());
        return response;
    }

    

}
