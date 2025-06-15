package com.example.enrollment_service.kafka.quiz;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.enrollment_service.dto.lesson_progress.LessonProgressRequestDTO;
import com.example.enrollment_service.service.enrollment.EnrollmentService;
import com.example.enrollment_service.service.lesson_progress.LessonProgressService;
import com.example.event.QuizAttemptEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuizEventListener {

    private final LessonProgressService lessonProgressService;
    private final EnrollmentService enrollmentService;

    @KafkaListener(topics = "quiz-attempt-event", groupId = "enrollment-consumer-group", containerFactory = "kafkaListenerContainerFactory")
    public void handleAddLessonProgress(QuizAttemptEvent event) {
        LessonProgressRequestDTO dto = new LessonProgressRequestDTO();
        dto.setUserId(event.getUserId());
        dto.setLessonId(event.getLessonId());  
        dto.setCourseId(event.getCourseId());
        dto.setCompleted(true);
        lessonProgressService.addLessonProgressForUser(dto);
        enrollmentService.updateProgressForUser(event.getUserId(), event.getCourseId());
    }
}
