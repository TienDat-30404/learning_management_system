package com.example.api_gateway.controller;

import com.example.api_gateway.dto.ApiResponseDTO;
import com.example.api_gateway.dto.CustomPageDTO;
import com.example.api_gateway.dto.aggregated.AggregatedCourseResponse;
import com.example.api_gateway.service.CourseAggregationService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/gateway/courses")
@RequiredArgsConstructor
public class CourseGatewayController {

    private final CourseAggregationService service;

    @GetMapping
    public Mono<ResponseEntity<ApiResponseDTO<CustomPageDTO<AggregatedCourseResponse>>>> getCourses(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return service.getCoursesWithUsers(page, size)
                .map(data -> ResponseEntity.ok(
                        new ApiResponseDTO<>(200, data, "Lấy danh sách thành công")));
    }
}