package com.example.payment_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.payment_service.context.AuthenticatedUser;
import com.example.payment_service.dto.ApiResponseDTO;
import com.example.payment_service.dto.payment.PaymentRequestDTO;
import com.example.payment_service.dto.payment.PaymentResponseDTO;
import com.example.payment_service.service.payment.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
@Controller
public class PaymentController {

    private final PaymentService paymentService;
    private final AuthenticatedUser authenticatedUser;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<PaymentResponseDTO>> createPaymentForCouse(
            @Valid @RequestBody PaymentRequestDTO request) {
        Long userId = authenticatedUser.getUserId();
        PaymentResponseDTO payment = paymentService.createPaymentForCourse(request, userId);
        ApiResponseDTO<PaymentResponseDTO> response = new ApiResponseDTO<>(
                201, payment, "Create payment for course successful");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
