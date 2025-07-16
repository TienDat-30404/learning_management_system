package com.example.enrollment_service.dto.lesson_progress;

import com.example.enrollment_service.dto.user.UserResponseDTO;

import lombok.Data;

@Data
public class LessonProgressResponseDTO {
    private Long id;
    // private UserResponseDTO user;
    private Long lessonId;
    private Boolean completed;
}
