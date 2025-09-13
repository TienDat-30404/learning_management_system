package com.example.quiz_service.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quiz_service.model.QuizAttempt;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    Page<QuizAttempt> findByUserId(Pageable pageable, Long userId);
    Boolean existsByUserIdAndQuizId(Long userId, Long lessonId);
    List<QuizAttempt> findAllByUserIdAndQuizId(Long userId, Long quizId);
}
