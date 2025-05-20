package com.example.review_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.review_service.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>  {
    
}
