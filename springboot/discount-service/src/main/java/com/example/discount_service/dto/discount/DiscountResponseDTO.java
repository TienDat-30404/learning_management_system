package com.example.discount_service.dto.discount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.discount_service.enums.DiscountType;

import lombok.Data;

@Data
public class DiscountResponseDTO {
    private Long id;

    private String code;

    private String description;

    private DiscountType discountType;

    private BigDecimal value;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer usageLimit; 

    private Integer usedCount;

    private Boolean isActive;

    private Boolean appliesToAllCourses;

    private Long createdBy;
}
