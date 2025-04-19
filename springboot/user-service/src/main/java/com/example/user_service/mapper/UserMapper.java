package com.example.user_service.mapper;

import org.springframework.stereotype.Component;

import com.example.user_service.dto.user.UserResponseDTO;
import com.example.user_service.dto.user.UserRequestDTO;
import com.example.user_service.model.User;
import com.example.user_service.model.Role;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;

import com.example.user_service.repository.RoleRepository;

@Data
@Component
public class UserMapper {
    private final RoleRepository roleRepository;


    public UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFullname(user.getFullname());
        dto.setEmail(user.getEmail());
        dto.setRole((user.getRole()));
        return dto;
    }

    public User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setFullname(dto.getFullname());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        Role role = roleRepository.findById(dto.getRole())
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + dto.getRole()));
        user.setRole(role);
        return user;
    }
}
