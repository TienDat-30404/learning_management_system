package com.example.user_service.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.user_service.dto.ApiResponseDTO;
import com.example.user_service.dto.role.RoleRequestDTO;
import com.example.user_service.dto.role.RoleResponseDTO;
import com.example.user_service.mapper.role.RoleMapper;
import com.example.user_service.model.Role;
import com.example.user_service.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public ApiResponseDTO<RoleResponseDTO> getAllRoles() {
        int status = 200;
        List<RoleResponseDTO> roles = roleRepository.findAll()
                .stream()
                .map(roleMapper::toDTO)
                .collect(Collectors.toList());
        return new ApiResponseDTO<>(status, roles);

    }

    public ApiResponseDTO<RoleResponseDTO> createRole(RoleRequestDTO requestDTO) {
        Role role = roleMapper.toEntity(requestDTO);
        role = roleRepository.save(role);
        RoleResponseDTO roleResponseDTO = roleMapper.toDTO(role);
        List<RoleResponseDTO> roleList = List.of(roleResponseDTO);
        int status = 201;
        return new ApiResponseDTO<>(status, roleList);
    }

}
