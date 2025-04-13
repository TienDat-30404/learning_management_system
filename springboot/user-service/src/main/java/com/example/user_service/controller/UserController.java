package com.example.user_service.controller;

import com.example.user_service.dto.user.UserResponseDTO;
import com.example.user_service.dto.ApiResponseDTO;
import com.example.user_service.dto.user.UserRequestDTO;
import com.example.user_service.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping
    public ApiResponseDTO<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @PostMapping
    public ApiResponseDTO<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.createUser(userRequestDTO);
    }
}
