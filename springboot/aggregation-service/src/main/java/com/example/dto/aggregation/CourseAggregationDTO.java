package com.example.dto.aggregation;


import java.math.BigDecimal;

import com.example.dto.CategoryDTO;
import com.example.dto.DiscountDTO;
import com.example.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CourseAggregationDTO {
    private Long id;
    private String title;
    private String description;
    private String image;
    private String learningOutcomes;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;
    private CategoryDTO category;
    private UserDTO user;
    private BigDecimal price;

    private DiscountDTO discount;
    
}
