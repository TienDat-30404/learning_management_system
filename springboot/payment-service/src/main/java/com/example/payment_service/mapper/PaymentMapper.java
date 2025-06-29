package com.example.payment_service.mapper;

import org.springframework.stereotype.Component;

import com.example.payment_service.dto.payment.PaymentRequestDTO;
import com.example.payment_service.dto.payment.PaymentResponseDTO;
import com.example.payment_service.dto.paymentMethod.PaymentMethodResposneDTO;
import com.example.payment_service.model.Payment;

import lombok.Data;

@Component
@Data
public class PaymentMapper {
    private final PaymentMethodMapper paymentMethodMapper;
    public PaymentResponseDTO toDTO(Payment payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO(); 
        dto.setId(payment.getId());
        dto.setUserId(payment.getUserId());
        dto.setCourseId(payment.getCourseId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setPaymentDate(payment.getPaymentDate());

        PaymentMethodResposneDTO paymentMethod = paymentMethodMapper.toDTO(payment.getPaymentMethod());
        dto.setPaymentMethod(paymentMethod);
        return dto;
    }

    public Payment toEntity(PaymentRequestDTO request) {
        Payment payment = new Payment();
        payment.setAmount(request.getAmount());
        payment.setPaymentStatus(request.getPaymentStatus());
        payment.setPaymentDate(request.getPaymentDate());
        payment.setCourseId(request.getCourseId());
        return payment;
    }
}
