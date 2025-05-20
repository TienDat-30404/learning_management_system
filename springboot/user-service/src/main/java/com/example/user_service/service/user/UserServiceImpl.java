package com.example.user_service.service.user;

import com.example.user_service.model.Role;
import com.example.user_service.model.User;
import com.example.user_service.dto.user.UserResponseDTO;
import com.example.user_service.dto.user.UserUpdateDTO;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.dto.CustomPageDTO;
import com.example.user_service.dto.user.UserRequestDTO;
import com.example.user_service.repository.RoleRepository;
import com.example.user_service.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    // @Cacheable(value = "users", key = "'page=' + #pageable.pageNumber + '&size='
    // + #pageable.pageSize")
    public CustomPageDTO<UserResponseDTO> getAllUsers(Pageable pageable) {

        Page<User> userPage = userRepository.findAll(pageable);
        Page<UserResponseDTO> users = userPage.map(userMapper::toDTO);
        return new CustomPageDTO<>(
                users.getContent(),
                users.getTotalElements(),
                users.getTotalPages());
    }

    // @CacheEvict(value = "users", allEntries = true)
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    // @Cacheable(value = "users", key = "'detaiUser=' + #id")
    public UserResponseDTO getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return userMapper.toDTO(user);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public List<UserResponseDTO> getUserByIds(List<Long> ids) {
        List<User> users = userRepository.findAllById(ids);
        List<UserResponseDTO> response = users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
        return response;
    }

    // @CacheEvict(value = "users", allEntries = true)
    public UserResponseDTO updateUser(Long id, UserUpdateDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy user với id : " + id));
        Role role = null;
        if (request.getRole() != null) {
            role = roleRepository.findById(request.getRole())
                    .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + request.getRole()));
            user.setRole(role);
        }
        user.setRole(role);
        userMapper.updateUserFromDTO(request, user);
        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }
}
