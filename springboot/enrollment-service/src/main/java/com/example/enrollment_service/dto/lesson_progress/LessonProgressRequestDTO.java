package com.example.enrollment_service.dto.lesson_progress;

import lombok.Data;

@Data
public class LessonProgressRequestDTO {
    private Long userId;
    private Long lessonId;
    private Long courseId;
    private Boolean completed;
}
