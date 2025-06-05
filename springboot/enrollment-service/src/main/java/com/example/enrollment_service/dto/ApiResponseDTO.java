package com.example.enrollment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDTO<T> {
    private int stauts;
    private T data;
    private String message;
}
