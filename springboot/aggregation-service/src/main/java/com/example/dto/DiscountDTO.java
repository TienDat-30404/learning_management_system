package com.example.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DiscountDTO {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long courseId;

    private BigDecimal value;
    private String discountType;
}
