package com.example.enrollment_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.enrollment_service.config.FeignClientConfig;
import com.example.enrollment_service.dto.ApiResponseDTO;
import com.example.enrollment_service.dto.user.UserResponseDTO;

@FeignClient(name = "user-service", configuration = FeignClientConfig.class)
public interface UserClient {

    @GetMapping("/api/v1/users/{id}")
    ApiResponseDTO<UserResponseDTO> getUserById(@PathVariable("id") Long id);

    @GetMapping("/api/v1/users/{userId}/exists")
    Boolean checkExistUser(@PathVariable("userId") Long userId);
}
