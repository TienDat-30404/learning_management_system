package com.example.enrollment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.enrollment_service.model.LessonProgress;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long>  {
    long countByUserIdAndCourseIdAndCompletedTrue(Long userId, Long courseId);
}
