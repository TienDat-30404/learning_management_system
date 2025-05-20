package com.example.user_service.dto.auth;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@ToString(exclude = "password")
public class LoginRequestDTO {
    @NotEmpty(message = "Username không được để trống")
    private String userName;

    @NotEmpty(message = "Password không được để trống")
    private String password;
}
