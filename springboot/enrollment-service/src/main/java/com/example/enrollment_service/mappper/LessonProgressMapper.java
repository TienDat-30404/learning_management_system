package com.example.enrollment_service.mappper;

import org.springframework.stereotype.Component;

import com.example.enrollment_service.dto.lesson_progress.LessonProgressRequestDTO;
import com.example.enrollment_service.dto.lesson_progress.LessonProgressResponseDTO;
import com.example.enrollment_service.model.LessonProgress;

@Component
public class LessonProgressMapper {

    public LessonProgressResponseDTO toDTO(LessonProgress lessonProgress) {
        LessonProgressResponseDTO dto = new LessonProgressResponseDTO();
        dto.setId(lessonProgress.getId());
        dto.setCompleted(lessonProgress.getCompleted());
        dto.setLessonId(lessonProgress.getLessonId());
        return dto;
    }

    public LessonProgress toEntity(LessonProgressRequestDTO request) {
        LessonProgress lessonProgress = new LessonProgress();
        lessonProgress.setUserId(request.getUserId());
        lessonProgress.setLessonId(request.getLessonId());
        lessonProgress.setCourseId(request.getCourseId());
        lessonProgress.setCompleted(request.getCompleted());
        return lessonProgress;
    }
}
