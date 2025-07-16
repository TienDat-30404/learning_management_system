package com.example.review_service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.review_service.enums.TargetType;
import com.example.review_service.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>  {
    Page<Review> findByTargetIdAndTargetType(Pageable pageable, Long targetId, TargetType targetType);
}
