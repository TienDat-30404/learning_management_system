package com.example.quiz_service.service.quiz;

import org.springframework.data.domain.Pageable;

import com.example.quiz_service.dto.CustomPageDTO;
import com.example.quiz_service.dto.quiz.QuizRequestDTO;
import com.example.quiz_service.dto.quiz.QuizResponseDTO;

public interface QuizService {
    public QuizResponseDTO createQuiz(QuizRequestDTO request);
    public QuizResponseDTO getQuizForLesson(Long lessonId);
}
