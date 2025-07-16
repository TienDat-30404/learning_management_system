package com.example.api_gateway.dto.aggregated;

import com.example.api_gateway.dto.lesson_progress.LessonProgressGatewayResponse;

import lombok.Data;

@Data
public class AggregatedLessonProgressResponse {
    private Long id;
    private String title;
    private String content;
    private String videoUrl;
    // private LessonProgressGatewayResponse lessonProgress;
    private Boolean isCompleted;
}
