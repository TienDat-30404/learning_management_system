package com.example.user_service.dto.user;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "password")
public class UserUpdateDTO {
    private String userName;

    private String fullname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String gender;

    @Email(message = "Email không hợp lệ")
    private String email;

    @Size(min = 6, message = "Password phải có ít nhất 6 ký tự")
    private String password;

    private Long role;
}
