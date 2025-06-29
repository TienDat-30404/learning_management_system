package com.example.api_gateway.dto.aggregated;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.api_gateway.dto.course.CourseGatewayDTO;
import com.example.api_gateway.dto.paymentMethod.PaymentMethodGatewayResponse;
import com.example.api_gateway.dto.user.UserGatewayDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AggreatedPaymentResponse {
    private Long id;
    private UserGatewayDTO user;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long courseId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;
    
    private CourseGatewayDTO course;
    private BigDecimal amount;
    private String paymentStatus;
    private LocalDateTime paymentDate;
    private PaymentMethodGatewayResponse paymentMethod;
}
