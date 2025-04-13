package com.example.user_service.dto.user;
import com.example.user_service.model.Role;

import lombok.*;

@Getter
@Setter
@ToString(exclude = "password")

public class UserResponseDTO {
    private Long id;
    private String fullname;
    private String email;
    private String password;
    private Role role;
}