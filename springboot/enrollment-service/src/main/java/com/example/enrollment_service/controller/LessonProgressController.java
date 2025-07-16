package com.example.enrollment_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.enrollment_service.context.AuthenticatedUser;
import com.example.enrollment_service.dto.ApiResponseDTO;
import com.example.enrollment_service.dto.lesson_progress.LessonProgressRequestDTO;
import com.example.enrollment_service.dto.lesson_progress.LessonProgressResponseDTO;
import com.example.enrollment_service.service.lesson_progress.LessonProgressService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lesson-progress")
public class LessonProgressController {

    private final LessonProgressService lessonProgressService;
    private final AuthenticatedUser authenticatedUser;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<LessonProgressResponseDTO>> handleCompeletedLessonForUser(
            @Valid @RequestBody LessonProgressRequestDTO request) {
        Long userId = authenticatedUser.getUserId();
        LessonProgressResponseDTO lessonProgress = lessonProgressService.completedLessonForUser(request, userId);
        ApiResponseDTO<LessonProgressResponseDTO> response = new ApiResponseDTO<>(
                201, lessonProgress, "Lesson is completed by user with id =" + userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/total-lesson-completed")
    public ResponseEntity<Long> getTotalLessonCompleted(
            @RequestParam("courseId") Long courseId) {
        Long userId = authenticatedUser.getUserId();
        long total = lessonProgressService.getTotalLessonCompleted(userId, courseId);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/get-lesson-completed")
    public List<LessonProgressResponseDTO> getLessonCompleted(@RequestParam("lessonIds") List<Long> lessonIds) {
        Long userId = authenticatedUser.getUserId();
        return lessonProgressService.getLessonsCompleted(lessonIds, userId);
    }

}
