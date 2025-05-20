package com.example.course_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.course_service.model.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    
}
