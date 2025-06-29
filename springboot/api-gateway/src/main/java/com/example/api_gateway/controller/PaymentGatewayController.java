package com.example.api_gateway.controller;


import com.example.api_gateway.dto.ApiResponseDTO;
import com.example.api_gateway.dto.CustomPageDTO;
import com.example.api_gateway.dto.aggregated.AggreatedPaymentResponse;
import com.example.api_gateway.dto.payment.PaymentRequest;
import com.example.api_gateway.service.PaymentAggregationService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gateway/implement-payment")
@RequiredArgsConstructor
public class PaymentGatewayController {

    private final PaymentAggregationService service;

    @PostMapping
    public Mono<ResponseEntity<ApiResponseDTO<CustomPageDTO<AggreatedPaymentResponse>>>> implementPayment(
            @RequestHeader("Authorization") String token,
            @RequestBody PaymentRequest paymentRequest
            ) {
        return service.handleImplementPayment(token, paymentRequest)
                .map(data -> ResponseEntity.ok(
                        new ApiResponseDTO<>(200, data, "Thanh toán khóa học thành công")));
    }
}

