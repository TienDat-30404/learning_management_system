package com.example.payment_service.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.payment_service.dto.course.CourseResponseDTO;
import com.example.payment_service.dto.paymentMethod.PaymentMethodResposneDTO;
import com.example.payment_service.dto.user.UserResponseDTO;
import com.example.payment_service.enums.PaymentStatus;

import lombok.Data;

@Data
public class PaymentResponseDTO {
    private Long id;
    // private UserResponseDTO user;
    // private CourseResponseDTO course; 
    private Long userId;
    private Long courseId;
    private BigDecimal amount; 
    private PaymentStatus paymentStatus;
    private LocalDateTime paymentDate;
    private PaymentMethodResposneDTO paymentMethod;
}
