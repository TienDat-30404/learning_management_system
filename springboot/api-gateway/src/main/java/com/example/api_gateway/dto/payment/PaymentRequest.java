package com.example.api_gateway.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long courseId;
    private BigDecimal amount;
    private String paymentStatus;
    private LocalDateTime paymentDate;

    private Long paymentMethodId;
}
