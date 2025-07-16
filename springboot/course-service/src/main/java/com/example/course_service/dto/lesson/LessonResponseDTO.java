package com.example.course_service.dto.lesson;

import com.example.course_service.dto.course.CourseResponseDTO;

import lombok.Data;

@Data
public class LessonResponseDTO {
    private Long id;
    // private CourseResponseDTO course;
    // private Long courseId;
    private String title;
    private String content;
    private String videoUrl;

}
