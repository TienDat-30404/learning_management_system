package com.example.api_gateway.dto.aggregated;

import java.math.BigDecimal;

import com.example.api_gateway.dto.course.CourseGatewayDTO;
import com.example.api_gateway.dto.user.UserGatewayDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AggregatedEnrollmentResponse {
    private Long id;
    private Double progress;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long courseId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;

    private UserGatewayDTO user;
    private CourseGatewayDTO course;
}
