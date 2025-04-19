package com.example.course_service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomPageDTO<T> {
    private List<T> content;
    private Long totalElements;
    private Integer totalPages;
}
