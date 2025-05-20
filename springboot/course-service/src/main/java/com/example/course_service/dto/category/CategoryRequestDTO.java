package com.example.course_service.dto.category;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class CategoryRequestDTO {
    @NotBlank(message = "Tên thể loại không được để trống")
    private String name;
}
