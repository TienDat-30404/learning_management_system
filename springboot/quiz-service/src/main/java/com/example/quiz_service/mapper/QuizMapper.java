package com.example.quiz_service.mapper;

import org.springframework.stereotype.Component;

import com.example.quiz_service.dto.quiz.QuizRequestDTO;
import com.example.quiz_service.dto.quiz.QuizResponseDTO;
import com.example.quiz_service.model.Quiz;

@Component
public class QuizMapper {
    public QuizResponseDTO toDTO(Quiz quiz) {
        QuizResponseDTO dto = new QuizResponseDTO();
        dto.setId(quiz.getId());
        dto.setTitle(quiz.getTitle());
        dto.setDescription(quiz.getDescription());
        return dto;
    }

    public Quiz toEntity(QuizRequestDTO request) {
        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setLessonId(request.getLessonId());
        return quiz;
    }
}
