package com.example.user_service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.user_service.dto.ApiResponseDTO;
import com.example.user_service.dto.auth.AuthResponseDTO;
import com.example.user_service.dto.auth.LoginRequestDTO;
import com.example.user_service.dto.user.UserRequestDTO;
import com.example.user_service.dto.user.UserResponseDTO;
import com.example.user_service.service.auth.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthController {
    @Autowired
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO<UserResponseDTO>>> login(
            @Valid @RequestBody LoginRequestDTO request) {
        AuthResponseDTO<UserResponseDTO> authResponseDTO = authService.login(request);
        ApiResponseDTO<AuthResponseDTO<UserResponseDTO>> response = new ApiResponseDTO<>(200, authResponseDTO,
                "Login successful");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> register(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO userResponseDTO = authService.register(request);
        ApiResponseDTO<UserResponseDTO> response = new ApiResponseDTO<>(201, userResponseDTO, "Register successful");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
