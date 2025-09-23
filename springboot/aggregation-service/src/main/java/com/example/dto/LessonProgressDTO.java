package com.example.dto;

import lombok.Data;

@Data
public class LessonProgressDTO {
    private Long id;
    private Long lessonId;
    private Boolean completed;
}
