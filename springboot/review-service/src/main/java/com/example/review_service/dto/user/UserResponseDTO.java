package com.example.review_service.dto.user;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String fullname;
    private String email;
}
