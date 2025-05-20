package com.example.user_service.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO<T> {
    private String accessToken;
    private String refreshToken;
    private T userInfo;
  
    
}
