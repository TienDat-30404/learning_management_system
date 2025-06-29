package com.example.review_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.review_service.config.FeignClientConfig;
import com.example.review_service.dto.ApiResponseDTO;
import com.example.review_service.dto.user.UserResponseDTO;

@FeignClient(name = "user-service", configuration = FeignClientConfig.class)
public interface UserClient {
    @GetMapping("/api/v1/users/by-ids")
    ApiResponseDTO<List<UserResponseDTO>> getUserByIds(@RequestParam("ids") List<Long> userIds);

    @GetMapping("/api/v1/users/{userId}")
    ApiResponseDTO<UserResponseDTO> getUserById(@RequestParam("userId") Long userId);

    @GetMapping("/api/v1/users/{userId}/exists")
    Boolean checkExistUser(@PathVariable("userId") Long userId, @RequestHeader("API_KEY_INTERNAL") String apiInternal);

}
