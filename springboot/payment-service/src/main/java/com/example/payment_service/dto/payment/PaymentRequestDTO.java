package com.example.payment_service.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.payment_service.enums.PaymentStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDTO {
  
    @NotNull(message = "Course ID cannot be null")
    private Long courseId;

    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private LocalDateTime paymentDate;

    @NotNull(message = "Payment Method ID cannot be null")
    private Long paymentMethodId;
}
