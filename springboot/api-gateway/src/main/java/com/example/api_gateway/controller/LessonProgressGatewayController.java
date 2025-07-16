package com.example.api_gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.server.ServerWebExchange;

import com.example.api_gateway.dto.ApiResponseDTO;
import com.example.api_gateway.dto.CustomPageDTO;
import com.example.api_gateway.dto.aggregated.AggregatedCourseResponse;
import com.example.api_gateway.dto.aggregated.AggregatedLessonProgressResponse;
import com.example.api_gateway.service.CourseAggregationService;
import com.example.api_gateway.service.LessonProgressAggregationService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/gateway/lesson_progress")
@RequiredArgsConstructor
public class LessonProgressGatewayController {

    private final LessonProgressAggregationService service;

    @GetMapping
    public Mono<ResponseEntity<ApiResponseDTO<CustomPageDTO<AggregatedLessonProgressResponse>>>> getCourses(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "courseId") Long courseId,
            @RequestHeader("Authorization") String token,
            ServerWebExchange exchange)

    {
        return service.getLessonProgressOfUser(page, size, courseId, token, exchange)
                .map(data -> ResponseEntity.ok(
                        new ApiResponseDTO<>(200, data, "Lấy danh sách thành công")));
    }

}
