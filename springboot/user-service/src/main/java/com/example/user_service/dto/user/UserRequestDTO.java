package com.example.user_service.dto.user;

import lombok.*;

@Data
@ToString(exclude = "password")
public class UserRequestDTO {
    private String fullname;
    private String email;
    private String password; 
    private Long role;
}
