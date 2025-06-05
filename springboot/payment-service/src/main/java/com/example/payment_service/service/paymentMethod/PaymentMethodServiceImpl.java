package com.example.payment_service.service.paymentMethod;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.payment_service.dto.CustomPageDTO;
import com.example.payment_service.dto.paymentMethod.PaymentMethodRequestDTO;
import com.example.payment_service.dto.paymentMethod.PaymentMethodResposneDTO;
import com.example.payment_service.mapper.PaymentMethodMapper;
import com.example.payment_service.model.PaymentMethod;
import com.example.payment_service.repository.PaymentMethodRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;
    public CustomPageDTO<PaymentMethodResposneDTO> getAllPaymentMethods(Pageable pageable) {
        Page<PaymentMethod> paymentMethodsPage = paymentMethodRepository.findAll(pageable);
        Page<PaymentMethodResposneDTO> paymentMethods = paymentMethodsPage.map(paymentMethodMapper::toDTO);
        return new CustomPageDTO<>(
            paymentMethods.getContent(),
            paymentMethods.getTotalElements(),
            paymentMethods.getTotalPages()
        );
    }

    public PaymentMethodResposneDTO addPaymentMethod(PaymentMethodRequestDTO request) {
        PaymentMethod paymentMethod = paymentMethodMapper.toEntity(request);
        paymentMethod = paymentMethodRepository.save(paymentMethod);

        PaymentMethodResposneDTO response = paymentMethodMapper.toDTO(paymentMethod);
        return response;
    }

    public PaymentMethodResposneDTO updatePaymentMethod(Long id, PaymentMethodRequestDTO request) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment Method not found with id = " + id));
        paymentMethod.setName(request.getName());
        PaymentMethod updatedPaymentMethod = paymentMethodRepository.save(paymentMethod);
        PaymentMethodResposneDTO response = paymentMethodMapper.toDTO(updatedPaymentMethod);
        return response;
    }
    
}
