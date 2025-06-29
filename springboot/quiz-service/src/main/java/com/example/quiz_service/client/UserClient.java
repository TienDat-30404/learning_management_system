package com.example.quiz_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.quiz_service.config.FeignClientConfig;
import com.example.quiz_service.dto.ApiResponseDTO;
import com.example.quiz_service.dto.user.UserResponseDTO;

@FeignClient(name = "user-service", configuration = FeignClientConfig.class)
public interface UserClient {

    @GetMapping("/api/v1/users/{id}/exists")
    Boolean checkExistUser(@PathVariable("id") Long id);

    @GetMapping("/api/v1/users/{id}")
    ApiResponseDTO<UserResponseDTO> getUserById(@PathVariable("id") Long id);
}
