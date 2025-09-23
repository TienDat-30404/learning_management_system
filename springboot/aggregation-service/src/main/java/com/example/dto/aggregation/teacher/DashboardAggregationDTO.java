package com.example.dto.aggregation.teacher;

import com.example.dto.CategoryDTO;

import lombok.Data;

@Data
public class DashboardAggregationDTO {
    private Long id;
    private String title;
    private String description;
    private String image;
    private Long totalLesson;
    private CategoryDTO category;
    private Long quantityStudent;
}
