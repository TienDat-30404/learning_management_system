package com.example.course_service.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_service.client.UserClient;
import com.example.course_service.dto.ApiResponseDTO;
import com.example.course_service.dto.CustomPageDTO;
import com.example.course_service.dto.category.CategoryResponseDTO;
import com.example.course_service.dto.course.CourseResponseDTO;
import com.example.course_service.dto.course.CourseResquestDTO;
import com.example.course_service.dto.user.UserResponseDTO;
import com.example.course_service.model.Course;
import com.example.course_service.service.CourseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<CourseResponseDTO>> createCourse(
            @Valid @RequestBody CourseResquestDTO courseResquestDTO) {
        CourseResponseDTO courseResponseDTO = courseService.createCourse(courseResquestDTO);
        ApiResponseDTO<CourseResponseDTO> response = new ApiResponseDTO<>(
                HttpStatus.CREATED.value(), courseResponseDTO, "Course created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<CustomPageDTO<CourseResponseDTO>>> getAllCourses(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        CustomPageDTO<CourseResponseDTO> courses = courseService.getAllCourses(pageable);
        ApiResponseDTO<CustomPageDTO<CourseResponseDTO>> response = new ApiResponseDTO<>(200, courses,
                "Courses retrieved successfully");
        return ResponseEntity.ok(response);
    }

}
