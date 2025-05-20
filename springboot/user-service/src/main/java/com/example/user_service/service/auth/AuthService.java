package com.example.user_service.service.auth;
import com.example.user_service.dto.auth.AuthResponseDTO;
import com.example.user_service.dto.auth.LoginRequestDTO;
import com.example.user_service.dto.user.UserRequestDTO;
import com.example.user_service.dto.user.UserResponseDTO;


public interface AuthService {
 
    public AuthResponseDTO<UserResponseDTO> login(LoginRequestDTO loginRequestDTO);

    public UserResponseDTO register(UserRequestDTO userRequestDTO);
}