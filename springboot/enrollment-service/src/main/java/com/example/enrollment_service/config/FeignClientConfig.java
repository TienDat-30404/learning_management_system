package com.example.enrollment_service.config;

import feign.RequestInterceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Value("${internal.api.key}")
    private String apiKeyInternal;

    @Bean
    public RequestInterceptor requestInterceptor() {
        System.out.println("apiKEYINTERNALAAAAAAAAAAAAAAAAAAA" + apiKeyInternal);
        return requestTemplate -> {
            requestTemplate.header("API_KEY_INTERNAL", apiKeyInternal);
        };
    }
}