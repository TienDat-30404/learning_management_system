package com.example.enrollment_service.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@FeignClient(name = "course-service")
public interface CourseClient {

    @GetMapping("/api/v1/lessons/total-lesson")
    Long getTotalLessonInCourse(@RequestParam("courseId") Long courseId);
}
