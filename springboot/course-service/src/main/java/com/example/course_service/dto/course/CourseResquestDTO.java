package com.example.course_service.dto.course;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import com.example.course_service.dto.category.CategoryResponseDTO;
import com.example.course_service.dto.user.UserResponseDTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseResquestDTO {

    @NotBlank(message = "Tên không được để trống")
    private String title;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    private MultipartFile image;

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;

    @NotNull(message = "User ID cannot be null")
    private Long userId;
    

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;
}