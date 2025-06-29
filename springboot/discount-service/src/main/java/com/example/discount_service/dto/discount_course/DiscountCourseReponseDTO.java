package com.example.discount_service.dto.discount_course;

import java.math.BigDecimal;

import com.example.discount_service.enums.DiscountType;

import lombok.Data;

@Data
public class DiscountCourseReponseDTO {
    private Long courseId;
    private BigDecimal value;
    private DiscountType discountType;
}
