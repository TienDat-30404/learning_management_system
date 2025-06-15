package com.example.enrollment_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.enrollment_service.context.AuthenticatedUser;
import com.example.enrollment_service.dto.ApiResponseDTO;
import com.example.enrollment_service.dto.lesson_progress.LessonProgressRequestDTO;
import com.example.enrollment_service.dto.lesson_progress.LessonProgressResponseDTO;
import com.example.enrollment_service.service.lesson_progress.LessonProgressService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/lesson-progress")
public class LessonProgressController {
    
    private final LessonProgressService lessonProgressService;
    private final AuthenticatedUser authenticatedUser;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<LessonProgressResponseDTO>> handleCompeletedLessonForUser(
        @Valid @RequestBody LessonProgressRequestDTO request
    ) {
        Long userId = authenticatedUser.getUserId();
        LessonProgressResponseDTO lessonProgress = lessonProgressService.completedLessonForUser(request, userId);
        ApiResponseDTO<LessonProgressResponseDTO> response = new ApiResponseDTO<>(
            201, lessonProgress, "Lesson is completed by user with id =" + userId
        );
        return ResponseEntity.ok(response);
    }

}
