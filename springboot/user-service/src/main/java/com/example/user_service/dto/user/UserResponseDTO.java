package com.example.user_service.dto.user;

import java.time.LocalDate;

import com.example.user_service.dto.role.RoleResponseDTO;

import lombok.*;

@Data
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String birthDate;
    private String gender;
    private String email;
    private RoleResponseDTO role;
}