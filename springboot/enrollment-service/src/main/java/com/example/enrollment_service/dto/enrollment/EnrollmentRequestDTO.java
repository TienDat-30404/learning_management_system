package com.example.enrollment_service.dto.enrollment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnrollmentRequestDTO {
    @NotNull(message = "UserId can not be null")
    private Long userId; 

    @NotNull(message = "CourseId can not be null")
    private Long courseId;

    @NotNull(message = "Progress can not be null")
    private Double progress;
}
