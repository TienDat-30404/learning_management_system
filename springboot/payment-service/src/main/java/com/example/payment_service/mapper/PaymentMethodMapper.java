package com.example.payment_service.mapper;

import org.springframework.stereotype.Component;

import com.example.payment_service.dto.paymentMethod.PaymentMethodRequestDTO;
import com.example.payment_service.dto.paymentMethod.PaymentMethodResposneDTO;
import com.example.payment_service.model.PaymentMethod;


@Component
public class PaymentMethodMapper {
    public PaymentMethodResposneDTO toDTO(PaymentMethod paymentMethod) {
        PaymentMethodResposneDTO dto = new PaymentMethodResposneDTO();
        dto.setId(paymentMethod.getId());
        dto.setName(paymentMethod.getName());;
        return dto;
    }

    public PaymentMethod toEntity(PaymentMethodRequestDTO request) {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setName(request.getName());
        return paymentMethod;
    }
}
