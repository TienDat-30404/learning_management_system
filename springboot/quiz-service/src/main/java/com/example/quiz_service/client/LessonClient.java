package com.example.quiz_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quiz_service.config.FeignClientConfig;
import com.example.quiz_service.dto.ApiResponseDTO;
import com.example.quiz_service.dto.lesson.LessonResponseDTO;

@FeignClient(name = "course-service", configuration = FeignClientConfig.class)
public interface LessonClient {
    
    @GetMapping("/api/v1/lessons/{id}/exists")
    Boolean checkLessonById(@PathVariable("id") Long id);

    @GetMapping("/api/v1/lessons/{id}")
    ApiResponseDTO<LessonResponseDTO> getLessonById(@PathVariable("id") Long id);

    @GetMapping("/api/v1/lessons/course-id?lessonId={id}")
    Long findCourseIdByLessonId(@RequestParam("id") Long lessonId);
    
}
