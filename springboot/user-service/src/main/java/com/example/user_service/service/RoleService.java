package com.example.user_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.user_service.dto.ApiResponseDTO;
import com.example.user_service.dto.CustomPageDTO;
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

    @Cacheable(value = "roles", key = "'page' + #pageable.pageNumber + '&size=' + #pageable.pageSize")
    public CustomPageDTO<RoleResponseDTO> getAllRoles(Pageable pageable) {
        Page<Role> rolePage = roleRepository.findAll(pageable);
        Page<RoleResponseDTO> roles = rolePage.map(roleMapper::toDTO);
        return new CustomPageDTO<>(
                roles.getContent(),
                roles.getTotalElements(),
                roles.getTotalPages());
    }


    public RoleResponseDTO createRole(RoleRequestDTO requestDTO) {
        Role role = roleMapper.toEntity(requestDTO);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toDTO(savedRole);
    }

}
