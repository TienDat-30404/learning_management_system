package com.example.payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.payment_service.config.FeignClientConfig;


@FeignClient(name = "course-service", configuration = FeignClientConfig.class)
public interface CourseClient {
    @GetMapping("/api/v1/courses/{courseId}/exists")
    Boolean checkExistCourse(@PathVariable("courseId") Long courseId);
}
