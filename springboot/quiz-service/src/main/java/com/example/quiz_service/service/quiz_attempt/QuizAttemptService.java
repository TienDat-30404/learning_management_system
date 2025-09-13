package com.example.quiz_service.service.quiz_attempt;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.quiz_service.dto.CustomPageDTO;
import com.example.quiz_service.dto.quiz_attempt.QuizAttemptRequestDTO;
import com.example.quiz_service.dto.quiz_attempt.QuizAttemptResponseDTO;

public interface QuizAttemptService {
    public QuizAttemptResponseDTO createQuizAttempt(QuizAttemptRequestDTO request, Long userId);
    public CustomPageDTO<QuizAttemptResponseDTO> getResultQuizByUser(Pageable pageable, Long userId);
    public List<QuizAttemptResponseDTO> getHistoryQuizOfUser(Long userId, Long quizId);
    
}
