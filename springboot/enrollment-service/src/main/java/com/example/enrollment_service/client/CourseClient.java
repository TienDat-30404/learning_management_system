package com.example.enrollment_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.enrollment_service.dto.ApiResponseDTO;
import com.example.enrollment_service.dto.course.CourseResponseDTO;

@FeignClient(name = "course-service")
public interface CourseClient {

    @GetMapping("/api/v1/courses/by-ids")
    ApiResponseDTO<List<CourseResponseDTO>> getCourseByIds(@RequestParam("ids") List<Long> courseId);
}
