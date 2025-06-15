package com.example.quiz_service.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudinary.Api;
import com.example.quiz_service.context.AuthenticatedUser;
import com.example.quiz_service.dto.ApiResponseDTO;
import com.example.quiz_service.dto.CustomPageDTO;
import com.example.quiz_service.dto.quiz.QuizRequestDTO;
import com.example.quiz_service.dto.quiz_attempt.QuizAttemptRequestDTO;
import com.example.quiz_service.dto.quiz_attempt.QuizAttemptResponseDTO;
import com.example.quiz_service.service.quiz_attempt.QuizAttemptService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/quiz-attempts")
@Controller
@RequiredArgsConstructor
public class QuizAttemptController {
    private final QuizAttemptService quizAttemptService;
    private final AuthenticatedUser authenticatedUser;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<QuizAttemptResponseDTO>> createQuizAttempt(
        @Valid @RequestBody QuizAttemptRequestDTO request
    ) {
        Long userId = authenticatedUser.getUserId();
        QuizAttemptResponseDTO quizAttempt = quizAttemptService.createQuizAttempt(request, userId);
        ApiResponseDTO<QuizAttemptResponseDTO> response = new ApiResponseDTO<>(
            201, quizAttempt, "Create quiz attempt successful"
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<CustomPageDTO<QuizAttemptResponseDTO>>> getResultQuizByUser(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Long userId = authenticatedUser.getUserId();
        Pageable pageable = PageRequest.of(page, size);
        CustomPageDTO<QuizAttemptResponseDTO> quizAttempts = quizAttemptService.getResultQuizByUser(pageable, userId);
        ApiResponseDTO<CustomPageDTO<QuizAttemptResponseDTO>> response = new ApiResponseDTO<>(
            200, quizAttempts, "Result exam quiz for user with id = " + userId
        );
        return ResponseEntity.ok(response);
    }
}
