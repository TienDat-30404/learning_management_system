package com.example.course_service.service.lesson;


import org.springframework.data.domain.Pageable;

import com.example.course_service.dto.CustomPageDTO;
import com.example.course_service.dto.lesson.LessonRequestDTO;
import com.example.course_service.dto.lesson.LessonResponseDTO;
import com.example.course_service.dto.lesson.LessonUpdateDTO;

public interface LessonService {
    public CustomPageDTO<LessonResponseDTO> getAllLessons(Pageable pageable);

    public LessonResponseDTO createLesson(LessonRequestDTO request);

    public LessonResponseDTO updateLesson(Long id, LessonUpdateDTO request);
}
