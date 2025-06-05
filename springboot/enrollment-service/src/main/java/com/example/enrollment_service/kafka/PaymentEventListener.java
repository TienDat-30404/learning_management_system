package com.example.enrollment_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.enrollment_service.dto.enrollment.EnrollmentRequestDTO;
import com.example.enrollment_service.service.EnrollmentService;
import com.example.event.PaymentEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PaymentEventListener {
    private final EnrollmentService enrollmentService;
    
    @KafkaListener(
        topics = "payment-event",
        groupId = "payment-consumer-enrollment", 
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleAddErollmentOfPaymentEvent(PaymentEvent paymentEvent) {
        System.out.println("ngẻtwtwtrt" + paymentEvent);
        EnrollmentRequestDTO request = new EnrollmentRequestDTO();
        request.setUserId(paymentEvent.getUserId());
        request.setCourseId(paymentEvent.getCourseId());
        request.setProgress(Double.valueOf(0));
        
        enrollmentService.addEnrollment(request);
    }
}
