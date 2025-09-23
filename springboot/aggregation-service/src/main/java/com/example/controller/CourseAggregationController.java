package com.example.controller;



import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.ApiResponseDTO;
import com.example.dto.CustomPageDTO;
import com.example.dto.aggregation.CourseAggregationDTO;
import com.example.service.CourseAggregationService;


@RestController
@RequestMapping("/api/v1/aggregated/courses")
@RequiredArgsConstructor
public class CourseAggregationController {

    private final CourseAggregationService service;

    @GetMapping
    public Mono<ResponseEntity<ApiResponseDTO<CustomPageDTO<CourseAggregationDTO>>>> getCourses(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return service.getCoursesWithUsers(page, size)
                .map(data -> ResponseEntity.ok(
                        new ApiResponseDTO<>(200, data, "Lấy danh sách thành công")));
    }

     @GetMapping("/{courseId}")
    public Mono<ApiResponseDTO<CourseAggregationDTO>> getAggregatedCourseDetail(@PathVariable Long courseId) {
        return service.getCourseDetail(courseId)
                .map(course -> new ApiResponseDTO<>(200, course, "Chi tiết khóa học"));
    }
}