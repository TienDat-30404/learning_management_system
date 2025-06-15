package com.example.quiz_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quiz_service.model.QuizAnswer;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {
        public List<QuizAnswer> findByQuizQuestionId(Long quizId);
}
