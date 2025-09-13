package com.example.quiz_service.mapper;

import org.springframework.stereotype.Component;

import com.example.quiz_service.dto.quiz.QuizResponseDTO;
import com.example.quiz_service.dto.quiz_attempt.QuizAttemptRequestDTO;
import com.example.quiz_service.dto.quiz_attempt.QuizAttemptResponseDTO;
import com.example.quiz_service.model.QuizAttempt;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuizAttemptMapper {
    private final QuizMapper quizMapper;
    
    public QuizAttemptResponseDTO toDTO(QuizAttempt quizAttempt) {
        QuizAttemptResponseDTO dto = new QuizAttemptResponseDTO();
        dto.setId(quizAttempt.getId());
        dto.setScore(quizAttempt.getScore());
        dto.setDuration(quizAttempt.getDuration());
        dto.setCreatedAt(quizAttempt.getCreatedAt());
        QuizResponseDTO quiz = quizMapper.toDTO(quizAttempt.getQuiz());
        dto.setQuiz(quiz);
        return dto;
        
    }

    public QuizAttempt toEntity(QuizAttemptRequestDTO request) {
        QuizAttempt quizAttempt = new QuizAttempt();
        quizAttempt.setScore(request.getScore());
        quizAttempt.setDuration(request.getDuration());
        return quizAttempt;
    }
}
