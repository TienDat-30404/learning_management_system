package com.example.user_service.service;

import com.example.user_service.model.User;
import com.example.user_service.dto.user.UserResponseDTO;
import com.example.user_service.dto.ApiResponseDTO;
import com.example.user_service.dto.user.UserRequestDTO;
import com.example.user_service.mapper.user.UserMapper;
import com.example.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public ApiResponseDTO<UserResponseDTO> getAllUsers() {
        int status = 200;
        List<UserResponseDTO> users = userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
        return new ApiResponseDTO<>(status, users);
    }

    public ApiResponseDTO<UserResponseDTO> createUser(UserRequestDTO requestDTO) {
        User user = userMapper.toEntity(requestDTO);
        user = userRepository.save(user);
        UserResponseDTO userReponseDTO = userMapper.toDTO(user);
        int status = 201;
        List<UserResponseDTO> userList = List.of(userReponseDTO);
        System.out.println(userList);
        return new ApiResponseDTO<>(status, userList);
    }

}
