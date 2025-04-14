package com.example.course_service.dto.course;
import java.math.BigDecimal;


import lombok.Data;


@Data
public class CourseResquestDTO {

    private String title;
    private String description;
    private String image;
    private Long categoryId;
    private Long userId;
    private BigDecimal price;
}