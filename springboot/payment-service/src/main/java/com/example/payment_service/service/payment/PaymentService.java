package com.example.payment_service.service.payment;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

import com.example.payment_service.dto.CustomPageDTO;
import com.example.payment_service.dto.payment.PaymentRequestDTO;
import com.example.payment_service.dto.payment.PaymentRequestVnpayDTO;
import com.example.payment_service.dto.payment.PaymentResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    // public CustomPageDTO<PaymentResponseDTO> getAllPayments(Pageable pageable);
    public PaymentResponseDTO createPaymentForCourse(PaymentRequestDTO request, Long userId);
    public String taoUrlThanhToan(HttpServletRequest request, PaymentRequestVnpayDTO thanhToanRequest) throws Exception ; 
}
