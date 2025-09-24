package com.example.enrollment_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.enrollment_service.model.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
     Page<Enrollment> findByUserId(Long userId, Pageable pageable);

     Optional<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId);

     @Query("SELECT COUNT(s) FROM Enrollment s WHERE s.courseId IN :courseIds GROUP BY s.courseId")
     List<Long> countStudentsGroupedByCourseId(List<Long> courseIds);

     @Query("SELECT s FROM Enrollment s WHERE s.courseId IN :courseIds ")
     Page<Enrollment> findStudentsByCourses(List<Long> courseIds, Pageable pageable);

     @Query("SELECT s FROM Enrollment s WHERE s.courseId IN :courseIds AND s.userId = :userId")
     Page<Enrollment> findStudentsByCoursesAndUserId(List<Long> courseIds, Long userId, Pageable pageable);

}
