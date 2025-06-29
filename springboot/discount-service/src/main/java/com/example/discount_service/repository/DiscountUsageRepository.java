package com.example.discount_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.discount_service.model.DiscountUsage;

@Repository
public interface DiscountUsageRepository extends JpaRepository<DiscountUsage, Long> {
    
}
