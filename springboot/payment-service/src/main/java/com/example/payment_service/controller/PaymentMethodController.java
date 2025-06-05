package com.example.payment_service.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudinary.Api;
import com.example.payment_service.dto.ApiResponseDTO;
import com.example.payment_service.dto.CustomPageDTO;
import com.example.payment_service.dto.paymentMethod.PaymentMethodRequestDTO;
import com.example.payment_service.dto.paymentMethod.PaymentMethodResposneDTO;
import com.example.payment_service.service.paymentMethod.PaymentMethodService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/paymentMethods")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<CustomPageDTO<PaymentMethodResposneDTO>>> getAllPaymentMethods(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size); 
        CustomPageDTO<PaymentMethodResposneDTO> paymentMethods = paymentMethodService.getAllPaymentMethods(pageable);
        ApiResponseDTO<CustomPageDTO<PaymentMethodResposneDTO>> response = new ApiResponseDTO<>(
            200, paymentMethods, "List payment method"
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping 
    public ResponseEntity<ApiResponseDTO<PaymentMethodResposneDTO>> addPaymentMethod(
        @Valid @RequestBody PaymentMethodRequestDTO request
    ) {
        PaymentMethodResposneDTO paymentMethod = paymentMethodService.addPaymentMethod(request);
        ApiResponseDTO<PaymentMethodResposneDTO> response = new ApiResponseDTO<>(
            201, paymentMethod, "Add Payment Method successful"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ApiResponseDTO<PaymentMethodResposneDTO>> updatePaymentMethod(
        @PathVariable Long id,
        @Valid @RequestBody PaymentMethodRequestDTO request
    ) {
        PaymentMethodResposneDTO paymentMethod = paymentMethodService.updatePaymentMethod(id, request);
        ApiResponseDTO<PaymentMethodResposneDTO> response = new ApiResponseDTO<>(
            200, paymentMethod, "Update payment method successful"
        );
        return ResponseEntity.ok(response);
    }

}
