package com.example.quiz_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quiz_service.dto.quiz.QuizResponseDTO;
import com.example.quiz_service.model.QuizQuestion;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
    public List<QuizQuestion> findByQuizId(Long quizId);
}
