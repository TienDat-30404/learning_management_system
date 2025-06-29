package com.example.api_gateway.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Object> handleWebClientResponseException(WebClientResponseException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatusCode());
        body.put("error", ex.getStatusText());
        body.put("path", "API Gateway call");

        try {
            Map<?, ?> parsedError = objectMapper.readValue(ex.getResponseBodyAsString(), Map.class);
            body.put("message", parsedError);
        } catch (Exception e) {
            body.put("message", ex.getResponseBodyAsString());
        }

        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

}
