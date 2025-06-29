package com.example.enrollment_service.controller;

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
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        CustomPageDTO<EnrollmentResponseDTO> enrollments = enrollmentService.getAllEnrollments(pageable);
        ApiResponseDTO<CustomPageDTO<EnrollmentResponseDTO>> response = new ApiResponseDTO<>(
            200, enrollments, "List enrollment successful"
        );

        return ResponseEntity.ok(response);
    }

    // @GetMapping("/by-user")
    // public ResponseEntity<ApiResponseDTO<CustomPageDTO<EnrollmentResponseDTO>>> getAllEnrollmentByUser(
    //     @RequestParam(value = "page", defaultValue = "0") int page, 
    //     @RequestParam(value = "size", defaultValue = "10") int size
    // ) {
    //     Long userId = authenticatedUser.getUserId();
    //     Pageable pageable = PageRequest.of(page, size);
    //     CustomPageDTO<EnrollmentResponseDTO> enrollments = enrollmentService.getAllEnrollmentByUser(userId, pageable); 
    //     ApiResponseDTO<CustomPageDTO<EnrollmentResponseDTO>> response = new ApiResponseDTO<>(
    //         200, enrollments, "Get list course of user when enrollment"
    //     );

    //     return ResponseEntity.ok(response);
    // }

    
}
