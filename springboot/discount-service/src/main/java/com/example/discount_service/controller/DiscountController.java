package com.example.discount_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.discount_service.dto.ApiResponseDTO;
import com.example.discount_service.dto.discount.DiscountRequestDTO;
import com.example.discount_service.dto.discount.DiscountResponseDTO;

import com.example.discount_service.service.discount.DiscountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discounts")
public class DiscountController {
    private final DiscountService discountService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<DiscountResponseDTO>> createDiscout(@Valid @RequestBody DiscountRequestDTO request) {
        DiscountResponseDTO discount = discountService.createDiscount(request);
        ApiResponseDTO<DiscountResponseDTO> response = new ApiResponseDTO<>(
            201, discount, "Create discount successful"
        );
        return ResponseEntity.ok(response);
    }
}
