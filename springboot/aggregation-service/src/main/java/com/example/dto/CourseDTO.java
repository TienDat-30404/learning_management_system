package com.example.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CourseDTO {
    private Long id;
    private String title;
    private BigDecimal price;
    private String learningOutcomes;
}
