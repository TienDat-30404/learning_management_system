package com.example.enrollment_service.mappper;

import org.springframework.stereotype.Component;

import com.example.enrollment_service.dto.enrollment.EnrollmentRequestDTO;
import com.example.enrollment_service.dto.enrollment.EnrollmentResponseDTO;
import com.example.enrollment_service.model.Enrollment;

@Component
public class EnrollmentMapper {

    public EnrollmentResponseDTO toDTO(Enrollment enrollment) {
        EnrollmentResponseDTO dto = new EnrollmentResponseDTO();
        dto.setId(enrollment.getId());
        dto.setCourseId(enrollment.getCourseId());
        dto.setUserId(enrollment.getUserId());
        dto.setProgress(enrollment.getProgress());
        return dto;
    }

    public Enrollment toEntity(EnrollmentRequestDTO request) {
        Enrollment enrollment = new Enrollment();
        enrollment.setUserId(request.getUserId());
        enrollment.setCourseId(request.getCourseId());
        enrollment.setProgress(request.getProgress());
        return enrollment;
    }
}
