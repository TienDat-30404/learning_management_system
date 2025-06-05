package com.example.notification_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.notification_service.dto.notification.NotificationRequestDTO;
import com.example.notification_service.service.NotificationService;
import com.example.event.PaymentEvent;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "payment-event", groupId = "payment-consumer-notification", containerFactory = "kafkaListenerContainerFactory")
    public void handlePaymentEvent(PaymentEvent event) {
        System.out.println("paymentEvent" + event);
        // Tạo DTO từ event nhận được
        NotificationRequestDTO request = new NotificationRequestDTO();
        request.setUserId(event.getUserId());
        request.setMessage(event.getMessage());
        request.setIsRead(false);

        notificationService.createNotification(request);
    }
}
