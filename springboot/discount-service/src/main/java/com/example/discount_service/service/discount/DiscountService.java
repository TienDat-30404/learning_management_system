package com.example.discount_service.service.discount;

import com.example.discount_service.dto.discount.DiscountRequestDTO;
import com.example.discount_service.dto.discount.DiscountResponseDTO;

public interface DiscountService {
    public DiscountResponseDTO createDiscount(DiscountRequestDTO request);
}
