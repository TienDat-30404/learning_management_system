package com.example.api_gateway.dto.lesson_progress;

import lombok.Data;

@Data
public class LessonProgressGatewayResponse {
    private Long id;
    private Long lessonId;
    private Boolean completed;
}
