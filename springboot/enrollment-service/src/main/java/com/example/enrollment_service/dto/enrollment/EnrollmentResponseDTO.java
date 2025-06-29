package com.example.enrollment_service.dto.enrollment;

import com.example.enrollment_service.dto.course.CourseResponseDTO;

import lombok.Data;

@Data
public class EnrollmentResponseDTO {
    private Long id ;
    private Long courseId;
    private Long userId;
    private Double progress;
}
