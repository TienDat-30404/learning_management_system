package com.example.user_service.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiKeyValidator {
    @Value("${internal.api.key}")
    private String validApiKey; 



    public boolean isValid(String apiKey) {
        return validApiKey.equals(apiKey);
    }
}