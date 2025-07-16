package com.example.enrollment_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.enrollment_service.model.LessonProgress;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {
    Long countByUserIdAndCourseIdAndCompletedTrue(Long userId, Long courseId);

    List<LessonProgress> findByLessonIdInAndUserIdAndCompletedTrue(List<Long> lessonIds, Long userId);
}