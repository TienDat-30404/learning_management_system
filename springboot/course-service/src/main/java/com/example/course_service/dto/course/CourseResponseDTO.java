package com.example.course_service.dto.course;

import java.math.BigDecimal;

import com.example.course_service.dto.user.UserResponseDTO;
import com.example.course_service.model.Category;

import lombok.Data;

@Data
public class CourseResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String image;
    private Category category;
    private UserResponseDTO user;
    private BigDecimal price;
}
