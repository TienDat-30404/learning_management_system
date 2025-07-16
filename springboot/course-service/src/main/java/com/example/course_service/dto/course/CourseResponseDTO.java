package com.example.course_service.dto.course;

import java.math.BigDecimal;

import com.example.course_service.dto.category.CategoryResponseDTO;
import com.example.course_service.dto.user.UserResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class CourseResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String image;
    private Long userId;
    private CategoryResponseDTO category;
    private String learningOutcomes;
    private BigDecimal price;
}
