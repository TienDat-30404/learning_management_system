package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.server.ServerWebExchange;

import com.example.dto.ApiResponseDTO;
import com.example.dto.CustomPageDTO;
import com.example.dto.aggregation.LessonProgressAggregationDTO;
import com.example.service.LessonProgressAggregationService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/aggregated/lesson_progress")
@RequiredArgsConstructor
public class LessonProgressAggregationController {

        private final LessonProgressAggregationService service;

        @GetMapping
        public Mono<ResponseEntity<ApiResponseDTO<CustomPageDTO<LessonProgressAggregationDTO>>>> getCourses(
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size,
                        @RequestParam(value = "courseId") Long courseId,
                        @RequestHeader("Authorization") String token,
                        ServerWebExchange exchange
                        )

        {
                String userId = exchange.getRequest().getHeaders().getFirst("X-User-ID");
                String userRoles = exchange.getRequest().getHeaders().getFirst("X-User-Roles");
                return service.getLessonProgressOfUser(page, size, courseId, token, userId, userRoles)
                                .map(data -> ResponseEntity.ok(
                                                new ApiResponseDTO<>(200, data, "Lấy danh sách thành công")));
        }

}
