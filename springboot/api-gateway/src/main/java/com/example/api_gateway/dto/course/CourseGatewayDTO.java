package com.example.api_gateway.dto.course;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CourseGatewayDTO {
    private Long id;
    private String title;
    private BigDecimal price;
    private String learningOutcomes;
}
