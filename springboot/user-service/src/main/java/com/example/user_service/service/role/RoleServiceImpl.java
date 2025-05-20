package com.example.user_service.service.role;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.example.user_service.dto.CustomPageDTO;
import com.example.user_service.dto.role.RoleRequestDTO;
import com.example.user_service.dto.role.RoleResponseDTO;
import com.example.user_service.mapper.RoleMapper;
import com.example.user_service.model.Role;
import com.example.user_service.repository.RoleRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    // @Cacheable(value = "roles", key = "'page=' + #pageable.pageNumber + '&size=' + #pageable.pageSize")
    public CustomPageDTO<RoleResponseDTO> getAllRoles(Pageable pageable) {
        Page<Role> rolePage = roleRepository.findAll(pageable);
        Page<RoleResponseDTO> roles = rolePage.map(roleMapper::toDTO);
        return new CustomPageDTO<>(
                roles.getContent(),
                roles.getTotalElements(),
                roles.getTotalPages());
    }


  

    // @CacheEvict(value = "roles", allEntries = true)
    public RoleResponseDTO createRole(RoleRequestDTO requestDTO) {
        Role role = roleMapper.toEntity(requestDTO);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toDTO(savedRole);
    }

    // @CacheEvict(value = "roles", allEntries = true)
    public RoleResponseDTO updateRole(Long id, RoleRequestDTO requestDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
        role.setName(requestDTO.getName());
        Role updatedRole = roleRepository.save(role);
        return roleMapper.toDTO(updatedRole);
    }

    // @CacheEvict(value = "roles", allEntries = true)
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
        roleRepository.delete(role);
    }

}
