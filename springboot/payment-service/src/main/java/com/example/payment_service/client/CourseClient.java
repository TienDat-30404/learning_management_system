package com.example.payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.payment_service.config.FeignClientConfig;
import com.example.payment_service.dto.ApiResponseDTO;
import com.example.payment_service.dto.course.CourseResponseDTO;

@FeignClient(name = "course-service", configuration = FeignClientConfig.class)
public interface CourseClient {

    @GetMapping("/api/v1/courses/{courseId}")
    ApiResponseDTO<CourseResponseDTO> getCourseById(@PathVariable("courseId") Long id);
}
