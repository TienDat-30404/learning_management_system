package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.example.dto.ApiResponseDTO;
import com.example.dto.CustomPageDTO;
import com.example.dto.aggregation.LessonProgressAggregationDTO;
import com.example.dto.aggregation.teacher.DashboardAggregationDTO;
import com.example.dto.aggregation.teacher.StudentAggregationDTO;
import com.example.service.TeacherAggregationService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/aggregated/teacher")
@RequiredArgsConstructor
public class TeacherAggregationController {

        private final TeacherAggregationService service;

        @GetMapping("/dashboard")
        public Mono<ResponseEntity<ApiResponseDTO<CustomPageDTO<DashboardAggregationDTO>>>> getCourses(
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size,
                        @RequestHeader("Authorization") String token,
                        ServerWebExchange exchange)

        {
                String userId = exchange.getRequest().getHeaders().getFirst("X-User-ID");
                String userRoles = exchange.getRequest().getHeaders().getFirst("X-User-Roles");
                return service.getCoursesWithUsers(page, size, token, userId, userRoles)
                                .map(data -> ResponseEntity.ok(
                                                new ApiResponseDTO<>(200, data, "Lấy danh sách thành công")));
        }

        @GetMapping("/list-students")
        public Mono<ResponseEntity<ApiResponseDTO<CustomPageDTO<StudentAggregationDTO>>>> getListStudentForTeacher(
                @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size,
                        @RequestParam(value = "userId", required = false) Long studentId,
                        @RequestHeader("Authorization") String token,
                        ServerWebExchange exchange
        ) {
                String userId = exchange.getRequest().getHeaders().getFirst("X-User-ID");
                String userRoles = exchange.getRequest().getHeaders().getFirst("X-User-Roles");
                return service.getAllStudentOfTeacher(page, size, token, userId, userRoles, studentId)
                                .map(data -> ResponseEntity.ok(
                                                new ApiResponseDTO<>(200, data, "Lấy danh sách thành công")));
        }
}
