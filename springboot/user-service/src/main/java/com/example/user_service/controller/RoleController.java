package com.example.user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_service.dto.ApiResponseDTO;
import com.example.user_service.dto.role.RoleRequestDTO;
import com.example.user_service.dto.role.RoleResponseDTO;
import com.example.user_service.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public ApiResponseDTO<RoleResponseDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping
    public ApiResponseDTO<RoleResponseDTO> createRole(@RequestBody RoleRequestDTO roleRequestDTO) {
        return roleService.createRole(roleRequestDTO);
    }
}
