package com.example.discount_service.dto.discount;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.example.discount_service.enums.DiscountType;

import lombok.Data;

@Data
public class DiscountRequestDTO {
    private String code;
    private Long createdBy;
    private String description;
    private DiscountType discountType;
    private BigDecimal value;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer usageLimit;
    private Integer usedCount;
    private List<Long> courseIds;

}
