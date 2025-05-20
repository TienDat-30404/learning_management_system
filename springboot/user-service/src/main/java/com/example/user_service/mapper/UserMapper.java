package com.example.user_service.mapper;


import org.springframework.stereotype.Component;

import com.example.user_service.dto.user.UserResponseDTO;
import com.example.user_service.dto.user.UserUpdateDTO;
import com.example.user_service.dto.role.RoleResponseDTO;
import com.example.user_service.dto.user.UserRequestDTO;
import com.example.user_service.model.User;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
public class UserMapper {
    private final RoleMapper roleMapper;

    public UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFullname(user.getFullname());
        dto.setEmail(user.getEmail());
        dto.setBirthDate(user.getBirthDate().toString());
        dto.setGender(user.getGender());

        RoleResponseDTO role = roleMapper.toDTO(user.getRole());
        dto.setRole(role);

        return dto;
    }

    public User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setFullname(dto.getFullname());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());
        user.setGender(dto.getGender());
        user.setPassword(dto.getPassword());
        return user;
    }

    public void updateUserFromDTO(UserUpdateDTO dto, User user) {
        if (dto.getFullname() != null) user.setFullname(dto.getFullname());
        if (dto.getGender() != null) user.setGender(dto.getGender());
        if (dto.getBirthDate() != null) user.setBirthDate(dto.getBirthDate());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());

    }
}
