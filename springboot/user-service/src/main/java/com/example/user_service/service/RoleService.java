package com.example.user_service.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.user_service.dto.ApiResponseDTO;
import com.example.user_service.dto.role.RoleRequestDTO;
import com.example.user_service.dto.role.RoleResponseDTO;
import com.example.user_service.mapper.RoleMapper;
import com.example.user_service.model.Role;
import com.example.user_service.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public ApiResponseDTO<List<RoleResponseDTO>> getAllRoles(Pageable pageable) {
        Page<Role> rolePage = roleRepository.findAll(pageable);
        List<RoleResponseDTO> roles = rolePage.stream()
                .map(roleMapper::toDTO)
                .collect(Collectors.toList());
        return new ApiResponseDTO<>(200, roles, rolePage.getTotalElements(), rolePage.getTotalPages());

    }

    public ApiResponseDTO<RoleResponseDTO> createRole(RoleRequestDTO requestDTO) {
        Role role = roleMapper.toEntity(requestDTO);
        role = roleRepository.save(role);   
        RoleResponseDTO roleResponseDTO = roleMapper.toDTO(role);
        return new ApiResponseDTO<>(201, roleResponseDTO);
    }

}
