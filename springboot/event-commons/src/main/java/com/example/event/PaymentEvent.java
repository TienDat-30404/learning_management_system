package com.example.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent {
    private Long paymentId;
    private Long userId;
    private Long courseId;
    private String message;
}