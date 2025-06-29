package com.example.api_gateway.controller;

import com.example.api_gateway.dto.ApiResponseDTO;
import com.example.api_gateway.dto.CustomPageDTO;
import com.example.api_gateway.dto.aggregated.AggregatedCourseResponse;
import com.example.api_gateway.dto.aggregated.AggregatedEnrollmentResponse;
import com.example.api_gateway.service.CourseAggregationService;
import com.example.api_gateway.service.EnrollmentAggregationService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ServerWebExchange;

@RestController
@RequestMapping("/api/v1/gateway/enrollments")
@RequiredArgsConstructor
public class EnrollmentGatewayController {

    private final EnrollmentAggregationService service;

    @GetMapping
    public Mono<ResponseEntity<ApiResponseDTO<CustomPageDTO<AggregatedEnrollmentResponse>>>> getCourses(
            @RequestHeader("Authorization") String token,
            ServerWebExchange exchange) {
        return service.getEnrollmentsWithCoursesAndUsers(token, exchange)
                .map(data -> ResponseEntity.ok(
                        new ApiResponseDTO<>(200, data, "Lấy danh sách thành công")));
    }
}
