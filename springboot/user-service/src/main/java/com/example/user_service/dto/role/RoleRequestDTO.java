package com.example.user_service.dto.role;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class RoleRequestDTO {
    @NotBlank(message = "Tên không được để trống")
    private String name;
}
