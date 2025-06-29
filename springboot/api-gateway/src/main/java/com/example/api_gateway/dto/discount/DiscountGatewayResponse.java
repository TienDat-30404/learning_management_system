package com.example.api_gateway.dto.discount;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DiscountGatewayResponse {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long courseId;

    private BigDecimal value;
    private String discountType;
}
