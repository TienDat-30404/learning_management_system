package com.example.enrollment_service.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.enrollment_service.model.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
     Page<Enrollment> findByUserId(Long userId, Pageable pageable);
}
