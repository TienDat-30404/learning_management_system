package com.example.user_service.service;

import com.example.user_service.model.User;
import com.example.user_service.dto.user.UserResponseDTO;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.dto.CustomPageDTO;
import com.example.user_service.dto.user.UserRequestDTO;
import com.example.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Cacheable(value = "users", key = "'page=' + #pageable.pageNumber + '&size=' + #pageable.pageSize")
    public CustomPageDTO<UserResponseDTO> getAllUsers(Pageable pageable) {

        Page<User> userPage = userRepository.findAll(pageable);
        Page<UserResponseDTO> users = userPage.map(userMapper::toDTO);
        return new CustomPageDTO<>(
                users.getContent(),
                users.getTotalElements(),
                users.getTotalPages());
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Cacheable(value = "users", key = "'getDetailUser-id=' + #id")
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

    @Cacheable(value = "users", key = "'getUserByIds-' + #ids")
    public List<UserResponseDTO> getUserByIds(List<Long> ids) {
        List<User> users = userRepository.findAllById(ids);
        List<UserResponseDTO> response = users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());

        return response;
    }
}
