package com.example.enrollment_service.service;

import org.springframework.data.domain.Pageable;

import com.example.enrollment_service.dto.CustomPageDTO;
import com.example.enrollment_service.dto.enrollment.EnrollmentRequestDTO;
import com.example.enrollment_service.dto.enrollment.EnrollmentResponseDTO;

public interface EnrollmentService  {
    public CustomPageDTO<EnrollmentResponseDTO> getAllEnrollments( Pageable pageable);
    public void addEnrollment(EnrollmentRequestDTO request);
    public CustomPageDTO<EnrollmentResponseDTO> getAllEnrollmentByUser(Long userId, Pageable pageable);
}
