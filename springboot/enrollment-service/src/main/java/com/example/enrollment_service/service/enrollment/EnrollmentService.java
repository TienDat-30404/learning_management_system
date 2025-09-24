package com.example.enrollment_service.service.enrollment;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.enrollment_service.dto.CustomPageDTO;
import com.example.enrollment_service.dto.enrollment.EnrollmentRequestDTO;
import com.example.enrollment_service.dto.enrollment.EnrollmentResponseDTO;
import com.example.enrollment_service.dto.enrollment.EnrollmentUpdateDTO;

public interface EnrollmentService {
    public CustomPageDTO<EnrollmentResponseDTO> getCourseProgressOfUser(Long userId, Pageable pageable);

    public void addEnrollment(EnrollmentRequestDTO request);

    // public CustomPageDTO<EnrollmentResponseDTO> getAllEnrollmentByUser(Long
    // userId, Pageable pageable);
    public void updateProgressForUser(Long userId, Long courseId);

    public List<Long> countStudentsByCourseId(List<Long> courseIds);

    public CustomPageDTO<EnrollmentResponseDTO> getEnrollments(List<Long> courseIds, Long userId, Pageable pageable);

}
