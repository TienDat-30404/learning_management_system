package com.example.course_service.dto.course;


import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;



import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
public class CourseUpdatetDTO {

    private String title;

    private String description;

    private MultipartFile image;

    private Long categoryId;

    private Long userId;

    private BigDecimal price;
}
