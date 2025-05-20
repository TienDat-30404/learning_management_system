package com.example.course_service.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudinary.Api;
import com.example.course_service.dto.ApiResponseDTO;
import com.example.course_service.dto.CustomPageDTO;
import com.example.course_service.dto.lesson.LessonRequestDTO;
import com.example.course_service.dto.lesson.LessonResponseDTO;
import com.example.course_service.dto.lesson.LessonUpdateDTO;
import com.example.course_service.service.lesson.LessonService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/lessons")
@Controller
public class LessonController {

    private final LessonService lessonService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<CustomPageDTO<LessonResponseDTO>>> getAllLessons(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        CustomPageDTO<LessonResponseDTO> lessons = lessonService.getAllLessons(pageable);
        ApiResponseDTO<CustomPageDTO<LessonResponseDTO>> response = new ApiResponseDTO<>(
                200, lessons, "List all lessons");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<LessonResponseDTO>> createLesson(
            @ModelAttribute @Valid LessonRequestDTO request) {
        LessonResponseDTO lesson = lessonService.createLesson(request);
        ApiResponseDTO<LessonResponseDTO> response = new ApiResponseDTO<>(
                201, lesson, "Create Lesson successful");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<LessonResponseDTO>> updateLesson(
            @PathVariable Long id,
            @ModelAttribute @Valid LessonUpdateDTO request) {
        LessonResponseDTO lesson = lessonService.updateLesson(id, request);
        ApiResponseDTO<LessonResponseDTO> response = new ApiResponseDTO<>(
                200, lesson, "Update Lesson Successful");
        return ResponseEntity.ok(response);
    }

}
