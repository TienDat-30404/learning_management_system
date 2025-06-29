package com.example.discount_service.mapper;

import org.springframework.stereotype.Component;

import com.example.discount_service.dto.discount.DiscountRequestDTO;
import com.example.discount_service.dto.discount.DiscountResponseDTO;
import com.example.discount_service.model.Discount;

@Component
public class DiscountMapper {
    public DiscountResponseDTO toDTO(Discount discount) {
        if (discount == null) {
            return null;
        }
        DiscountResponseDTO dto = new DiscountResponseDTO();
        dto.setId(discount.getId());
        dto.setCode(discount.getCode());
        dto.setDescription(discount.getDescription());
        dto.setDiscountType(discount.getDiscountType());
        dto.setValue(discount.getValue());
        dto.setStartDate(discount.getStartDate());
        dto.setEndDate(discount.getEndDate());
        dto.setUsageLimit(discount.getUsageLimit());
        dto.setUsedCount(discount.getUsedCount());
        dto.setIsActive(discount.getIsActive());
        dto.setAppliesToAllCourses(discount.getAppliesToAllCourses());
        dto.setCreatedBy(discount.getCreatedBy());
        return dto;
    }

    public Discount toEntity(DiscountRequestDTO request) {
        if (request == null) {
        return null;
    }
    Discount discount = new Discount();
    discount.setCode(request.getCode());
    discount.setDescription(request.getDescription());
    discount.setDiscountType(request.getDiscountType()); 
    discount.setValue(request.getValue());
    discount.setCreatedBy(request.getCreatedBy());
    discount.setStartDate(request.getStartDate());
    discount.setEndDate(request.getEndDate());
    discount.setUsageLimit(request.getUsageLimit());
    return discount;
    }
}
