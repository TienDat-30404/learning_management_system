package com.example.course_service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.course_service.model.Course;

import feign.Param;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findByUserId(Long userId, Pageable pageable);

}