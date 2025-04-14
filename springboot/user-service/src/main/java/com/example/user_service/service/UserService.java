package com.example.user_service.service;

import com.example.user_service.model.User;
import com.example.user_service.dto.user.UserResponseDTO;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.dto.ApiResponseDTO;
import com.example.user_service.dto.user.UserRequestDTO;
import com.example.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

     public ApiResponseDTO<List<UserResponseDTO>> getAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);  
        List<UserResponseDTO> users = userPage.stream()
                                              .map(userMapper::toDTO)
                                              .collect(Collectors.toList());

        return new ApiResponseDTO<>(200, users, userPage.getTotalElements(), userPage.getTotalPages());
    }

    public ApiResponseDTO<UserResponseDTO> createUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);
        user = userRepository.save(user);
        UserResponseDTO userReponseDTO = userMapper.toDTO(user);
        return new ApiResponseDTO<>(201, userReponseDTO);
    }

    public ApiResponseDTO<UserResponseDTO> getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserResponseDTO dto = userMapper.toDTO(user);
            return new ApiResponseDTO<>(200, dto);
        }
        return new ApiResponseDTO<>(404, null);
    }

}
