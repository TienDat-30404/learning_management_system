package com.example.quiz_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz_service.dto.quiz.QuizResponseDTO;
import com.example.quiz_service.model.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Quiz findByLessonId(Long lessonId);

    boolean existsByLessonId(Long lessonId);
}
