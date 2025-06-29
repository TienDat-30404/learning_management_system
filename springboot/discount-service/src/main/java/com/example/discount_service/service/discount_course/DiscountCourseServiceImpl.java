package com.example.discount_service.service.discount_course;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.discount_service.dto.discount_course.DiscountCourseReponseDTO;
import com.example.discount_service.model.Discount;
import com.example.discount_service.model.DiscountCourse;
import com.example.discount_service.repository.DiscountCourseRepository;
import com.example.discount_service.repository.DiscountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiscountCourseServiceImpl implements DiscountCourseService {

    private final DiscountCourseRepository discountCourseRepository;
    private final DiscountRepository discountRepository;

    public List<DiscountCourseReponseDTO> getDiscountsForCourses(List<Long> courseIds) {
        List<DiscountCourse> discountCourses  = discountCourseRepository.findByCourseIdInAndDiscountIsActiveTrue(courseIds);
        Optional<Discount> discountAllCourse = discountRepository.findByAppliesToAllCoursesTrueAndIsActiveTrue();
        Map<Long, DiscountCourse> discountCourseMap = discountCourses.stream()
            .collect(Collectors.toMap(DiscountCourse::getCourseId, dc -> dc));

        return courseIds.stream()
            .map(courseId -> {
                DiscountCourseReponseDTO dto = new DiscountCourseReponseDTO();
                dto.setCourseId(courseId);

                DiscountCourse specificDiscount = discountCourseMap.get(courseId);

                if (specificDiscount != null) {
                    dto.setValue(specificDiscount.getDiscount().getValue());
                    dto.setDiscountType(specificDiscount.getDiscount().getDiscountType());
                } else if (!discountAllCourse.isEmpty()) {
                    Discount generalDiscount = discountAllCourse.get(); 
                    dto.setValue(generalDiscount.getValue());
                    dto.setDiscountType(generalDiscount.getDiscountType());
                } else {
                    dto.setValue(null);
                    dto.setDiscountType(null);
                }

                return dto;
            })
            .collect(Collectors.toList());
    }

}
