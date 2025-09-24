package com.example.enrollment_service.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.enrollment_service.context.AuthenticatedUser;
import com.example.enrollment_service.dto.ApiResponseDTO;
import com.example.enrollment_service.dto.CustomPageDTO;
import com.example.enrollment_service.dto.enrollment.EnrollmentResponseDTO;
import com.example.enrollment_service.service.enrollment.EnrollmentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/enrollments")
@RestController
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    private final AuthenticatedUser authenticatedUser;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<CustomPageDTO<EnrollmentResponseDTO>>> getAllEnrollments(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Long userId = authenticatedUser.getUserId();
        Pageable pageable = PageRequest.of(page, size);
        CustomPageDTO<EnrollmentResponseDTO> enrollments = enrollmentService.getCourseProgressOfUser(userId, pageable);
        ApiResponseDTO<CustomPageDTO<EnrollmentResponseDTO>> response = new ApiResponseDTO<>(
                200, enrollments, "List enrollment successful");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all-student-by-courses")
    public ResponseEntity<ApiResponseDTO<CustomPageDTO<EnrollmentResponseDTO>>> getAllStudentForCoursess(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam("courseId") List<Long> courseIds,
            @RequestParam(required = false) Long userId
            ) {
        Pageable pageable = PageRequest.of(page, size);
        CustomPageDTO<EnrollmentResponseDTO> enrollments = enrollmentService.getEnrollments(courseIds, userId, pageable);
        ApiResponseDTO<CustomPageDTO<EnrollmentResponseDTO>> response = new ApiResponseDTO<>(
                200, enrollments, "List enrollment by courses of teacher successful");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-student-counts")
    public ResponseEntity<ApiResponseDTO<List<Long>>> getStudentCounts(@RequestParam("ids") List<Long> courseId) {
        List<Long> countStudents = enrollmentService.countStudentsByCourseId(courseId);
        ApiResponseDTO<List<Long>> response = new ApiResponseDTO<>(
                200, countStudents, "Get the number of students corresponding to each course");
        return ResponseEntity.ok(response);
    }

}
