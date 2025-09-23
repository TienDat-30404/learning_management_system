package com.example.enrollment_service.service.lesson_progress;

import java.util.List;

import com.example.enrollment_service.dto.lesson_progress.LessonProgressRequestDTO;
import com.example.enrollment_service.dto.lesson_progress.LessonProgressResponseDTO;

public interface LessonProgressService {
    public void addLessonProgressForUser(LessonProgressRequestDTO request);

    public LessonProgressResponseDTO completedLessonForUser(LessonProgressRequestDTO request, Long userId);

    public Long getTotalLessonCompleted(Long userId, Long courseId);

    public List<LessonProgressResponseDTO> getLessonsCompleted(List<Long> lessonIds, Long userId);


}
