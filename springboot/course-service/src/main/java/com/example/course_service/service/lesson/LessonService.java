package com.example.course_service.service.lesson;


import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.example.course_service.dto.CustomPageDTO;
import com.example.course_service.dto.lesson.LessonRequestDTO;
import com.example.course_service.dto.lesson.LessonResponseDTO;
import com.example.course_service.dto.lesson.LessonUpdateDTO;
import com.example.course_service.model.Lesson;

public interface LessonService {
    // public Map<String, Object> getAllLessonsOfCourse(Pageable pageable, Long courseId);
    public CustomPageDTO<LessonResponseDTO> getAllLessonsOfCourse(Pageable pageable, Long courseId);

    public LessonResponseDTO createLesson(LessonRequestDTO request);

    public LessonResponseDTO updateLesson(Long id, LessonUpdateDTO request);

    public Boolean checkLessonById(Long id);

    public LessonResponseDTO getLessonById(Long id);

    public Long totalLessonInCourse(Long lessonId);

    public Long getCourseIdBasedOnLessonId(Long lessonId);

    public List<Long> getLessonIdsByCourseIds(List<Long> courseIds);
}
