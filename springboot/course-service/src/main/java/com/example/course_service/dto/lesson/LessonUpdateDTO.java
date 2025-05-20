package com.example.course_service.dto.lesson;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class LessonUpdateDTO {
    private Long courseId;
    private String title;
    private String content;
    private MultipartFile videoUrl;
}
