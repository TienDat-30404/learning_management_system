package com.example.course_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDTO<T> {
    private int status;
    private T data;
    private Long totalElements;
    private Integer totalPages;

    public ApiResponseDTO(int status, T data) {
        this.status = status;
        this.data = data;
    }
}
