package com.example.payment_service.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration 
@Data    
public class VnPayConfig {

    @Value("${VNPAY_TMN_CODE}")
    private String tmnCode;

    @Value("${VNPAY_HASH_SECRET}")
    private String hashSecret;

    @Value("${VNPAY_URL}")
    private String url;

    @Value("${VNPAY_RETURN_URL}")
    private String returnUrl;
}