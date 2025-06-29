package com.example.discount_service.service.discount_course;

import java.util.List;
import java.util.Map;

import com.example.discount_service.dto.discount_course.DiscountCourseReponseDTO;

public interface DiscountCourseService {
    List<DiscountCourseReponseDTO> getDiscountsForCourses(List<Long> courseIds);
}
