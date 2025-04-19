package com.example.user_service.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_service.dto.ApiResponseDTO;
import com.example.user_service.dto.CustomPageDTO;
import com.example.user_service.dto.role.RoleRequestDTO;
import com.example.user_service.dto.role.RoleResponseDTO;
import com.example.user_service.mapper.RoleMapper;
import com.example.user_service.model.Role;
import com.example.user_service.service.RoleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<CustomPageDTO<RoleResponseDTO>>> getAllRoles(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        CustomPageDTO<RoleResponseDTO> roles = roleService.getAllRoles(pageable);

        ApiResponseDTO<CustomPageDTO<RoleResponseDTO>> response = new ApiResponseDTO<>(
                HttpStatus.CREATED.value(), roles, "Roles retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<RoleResponseDTO>> createRole(
            @Valid @RequestBody RoleRequestDTO roleRequestDTO) {
        RoleResponseDTO roleResponseDTO = roleService.createRole(roleRequestDTO);
        ApiResponseDTO<RoleResponseDTO> response = new ApiResponseDTO<>(
                HttpStatus.CREATED.value(), roleResponseDTO, "Role created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
