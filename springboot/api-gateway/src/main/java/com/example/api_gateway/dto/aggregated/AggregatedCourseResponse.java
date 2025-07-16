package com.example.api_gateway.dto.aggregated;

import java.math.BigDecimal;

import com.example.api_gateway.dto.category.CategoryGatewayDTO;
import com.example.api_gateway.dto.discount.DiscountGatewayResponse;
import com.example.api_gateway.dto.user.UserGatewayDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AggregatedCourseResponse {
    private Long id;
    private String title;
    private String description;
    private String image;
    private String learningOutcomes;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;
    private CategoryGatewayDTO category;
    private UserGatewayDTO user;
    private BigDecimal price;

    private DiscountGatewayResponse discount;
    
}
