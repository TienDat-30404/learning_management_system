package com.example.payment_service.service.paymentMethod;

import org.springframework.data.domain.Pageable;

import com.example.payment_service.dto.CustomPageDTO;
import com.example.payment_service.dto.paymentMethod.PaymentMethodRequestDTO;
import com.example.payment_service.dto.paymentMethod.PaymentMethodResposneDTO;
import com.example.payment_service.model.PaymentMethod;

public interface PaymentMethodService {

    public CustomPageDTO<PaymentMethodResposneDTO> getAllPaymentMethods(Pageable pageable);

    public PaymentMethodResposneDTO addPaymentMethod(PaymentMethodRequestDTO request);

    public PaymentMethodResposneDTO updatePaymentMethod(Long id, PaymentMethodRequestDTO request);
}
