package com.example.course_service.mapper;

import org.springframework.stereotype.Component;

import com.example.course_service.dto.course.CourseResponseDTO;
import com.example.course_service.dto.lesson.LessonRequestDTO;
import com.example.course_service.dto.lesson.LessonResponseDTO;
import com.example.course_service.dto.lesson.LessonUpdateDTO;
import com.example.course_service.model.Lesson;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class LessonMapper {
    private final CourseMapper courseMapper;
    public LessonResponseDTO toDTO(Lesson lesson) {
        LessonResponseDTO lessonResponse = new LessonResponseDTO();
        lessonResponse.setId(lesson.getId());
        lessonResponse.setTitle(lesson.getTitle());
        lessonResponse.setContent(lesson.getContent());
        lessonResponse.setVideoUrl(lesson.getVideoUrl());
        // CourseResponseDTO course = courseMapper.toDTO(lesson.getCourse());
        // lessonResponse.setCourse(course);
        return lessonResponse;
    }

    public Lesson toEntity(LessonRequestDTO request) {
        Lesson lesson = new Lesson();
        lesson.setTitle(request.getTitle());
        lesson.setContent(request.getContent());
        return lesson;
    }

    public void updateLessonFromDTO(LessonUpdateDTO request, Lesson lesson) {
        if(request.getTitle() != null) lesson.setTitle(request.getTitle());
        if(request.getContent() != null) lesson.setContent(request.getContent());
    }

}
