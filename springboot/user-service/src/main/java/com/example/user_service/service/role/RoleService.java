package com.example.user_service.service.role;
import org.springframework.data.domain.Pageable;
import com.example.user_service.dto.CustomPageDTO;
import com.example.user_service.dto.role.RoleRequestDTO;
import com.example.user_service.dto.role.RoleResponseDTO;


public interface RoleService {

    CustomPageDTO<RoleResponseDTO> getAllRoles(Pageable pageable);

    RoleResponseDTO createRole(RoleRequestDTO requestDTO);

    RoleResponseDTO updateRole(Long id, RoleRequestDTO requestDTO);

    void deleteRole(Long id);
} 