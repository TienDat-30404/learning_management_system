package com.example.user_service.controller;

import com.example.user_service.dto.user.UserResponseDTO;
import com.example.user_service.dto.user.UserUpdateDTO;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.ApiKeyValidator;
import com.example.user_service.service.user.UserService;
import com.example.user_service.dto.ApiResponseDTO;
import com.example.user_service.dto.CustomPageDTO;
import com.example.user_service.dto.user.UserRequestDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ApiKeyValidator apiKeyValidator;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<CustomPageDTO<UserResponseDTO>>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        CustomPageDTO<UserResponseDTO> users = userService.getAllUsers(pageable);
        ApiResponseDTO<CustomPageDTO<UserResponseDTO>> response = new ApiResponseDTO<>(
                200, users, "Users retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> createUser(
            @Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
        ApiResponseDTO<UserResponseDTO> response = new ApiResponseDTO<>(
                HttpStatus.CREATED.value(), userResponseDTO, "User created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getUserById(@PathVariable("id") Long id) {
        UserResponseDTO userResponseDTO = userService.getUserById(id);
        ApiResponseDTO<UserResponseDTO> response = new ApiResponseDTO<>(
                200, userResponseDTO, "User retrieved successfully");
        return ResponseEntity.ok(response);
    }

    // @GetMapping("/{userId}/exists")
    // public ResponseEntity<Boolean> checkUserExists(@PathVariable("userId") Long userId) {
    //     boolean exists = userService.existsById(userId);
    //     return ResponseEntity.ok(exists);
    // }

    @GetMapping("/by-ids")
    public ResponseEntity<ApiResponseDTO<List<UserResponseDTO>>> getUsersByIds(
            @RequestParam("ids") List<Long> userIds) {
        List<UserResponseDTO> users = userService.getUserByIds(userIds);
        ApiResponseDTO<List<UserResponseDTO>> response = new ApiResponseDTO<>(
                200, users, "Users retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> updateUser(
        @PathVariable Long id, 
        @Valid @RequestBody UserUpdateDTO request) {
        UserResponseDTO userResponseDTO = userService.updateUser(id, request);
        ApiResponseDTO<UserResponseDTO> response = new ApiResponseDTO<>(
            HttpStatus.OK.value(), userResponseDTO, "Cập nhật thông tin user thành công"
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }



    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/test")
    public ResponseEntity<String> testRedisConnection() {
        try {
            redisTemplate.opsForValue().set("testKey", "testValue");
            String value = redisTemplate.opsForValue().get("testKey");
            return ResponseEntity.ok("Redis connection successful! Value: " + value);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Redis connection failed: " + e.getMessage());
        }
    }


    @GetMapping("/{userId}/exists")
    public ResponseEntity<Boolean> checkExistUser(@PathVariable Long userId, 
                                                 @RequestHeader("API_KEY_INTERNAL") String apiKey) {
        if (!apiKeyValidator.isValid(apiKey)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid API Key");
        }
        boolean exists = userRepository.existsById(userId);
        return ResponseEntity.ok(exists);
    }
}
