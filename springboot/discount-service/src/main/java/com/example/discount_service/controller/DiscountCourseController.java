package com.example.discount_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.discount_service.dto.discount_course.DiscountCourseReponseDTO;
import com.example.discount_service.service.discount_course.DiscountCourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discounts")
public class DiscountCourseController {
    
    private final DiscountCourseService discountCourseService;

    @GetMapping("/get-discout-for-course")
    public List<DiscountCourseReponseDTO> getDiscountsByCourseIds(@RequestParam List<Long> courseIds) {
        return discountCourseService.getDiscountsForCourses(courseIds);
    }
}
