package com.example.payment_service.dto.payment;

import lombok.Data;

@Data
public class PaymentRequestVnpayDTO {
     private String content;
    private Long amount;  
}
