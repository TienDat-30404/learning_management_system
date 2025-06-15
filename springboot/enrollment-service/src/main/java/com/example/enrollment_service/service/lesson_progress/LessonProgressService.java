package com.example.enrollment_service.service.lesson_progress;

import com.example.enrollment_service.dto.lesson_progress.LessonProgressRequestDTO;
import com.example.enrollment_service.dto.lesson_progress.LessonProgressResponseDTO;

public interface LessonProgressService {
    public void addLessonProgressForUser(LessonProgressRequestDTO request);

    public LessonProgressResponseDTO completedLessonForUser(LessonProgressRequestDTO request, Long userId);
}
