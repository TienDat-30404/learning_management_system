package com.example.controller;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ServerWebExchange;

import com.example.dto.ApiResponseDTO;
import com.example.dto.CustomPageDTO;
import com.example.dto.aggregation.EnrollmentAggregationDTO;
import com.example.service.EnrollmentAggregationService;

@RestController
@RequestMapping("/api/v1/aggregated/enrollments")
@RequiredArgsConstructor
public class EnrollmentGatewayController {

    private final EnrollmentAggregationService service;

    @GetMapping
    public Mono<ResponseEntity<ApiResponseDTO<CustomPageDTO<EnrollmentAggregationDTO>>>> getCourses(
            @RequestHeader("Authorization") String token,
            ServerWebExchange exchange) {
        String userId = exchange.getRequest().getHeaders().getFirst("X-User-ID");
        String userRoles = exchange.getRequest().getHeaders().getFirst("X-User-Roles");
        return service.getEnrollmentsWithCoursesAndUsers(token, userId, userRoles)
                .map(data -> ResponseEntity.ok(
                        new ApiResponseDTO<>(200, data, "Lấy danh sách thành công")));
    }
}
