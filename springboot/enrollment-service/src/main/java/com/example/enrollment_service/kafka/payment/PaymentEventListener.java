package com.example.enrollment_service.kafka.payment;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.enrollment_service.dto.enrollment.EnrollmentRequestDTO;
import com.example.enrollment_service.service.enrollment.EnrollmentService;
import com.example.event.PaymentEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PaymentEventListener {
    private final EnrollmentService enrollmentService;
    
    @KafkaListener(
        topics = "payment-event",
        groupId = "enrollment-consumer-group", 
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleAddErollmentOfPaymentEvent(PaymentEvent paymentEvent) {
        System.out.println("0000000000000000000000000000" + paymentEvent.getUserId());
        EnrollmentRequestDTO request = new EnrollmentRequestDTO();
        request.setUserId(paymentEvent.getUserId());
        request.setCourseId(paymentEvent.getCourseId());
        request.setProgress(Double.valueOf(0));
        
        enrollmentService.addEnrollment(request);
    }
}
