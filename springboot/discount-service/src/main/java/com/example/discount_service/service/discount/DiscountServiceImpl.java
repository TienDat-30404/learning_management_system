package com.example.discount_service.service.discount;

import org.springframework.stereotype.Service;

import com.example.discount_service.dto.discount.DiscountRequestDTO;
import com.example.discount_service.dto.discount.DiscountResponseDTO;
import com.example.discount_service.mapper.DiscountMapper;
import com.example.discount_service.model.Discount;
import com.example.discount_service.model.DiscountCourse;
import com.example.discount_service.repository.DiscountCourseRepository;
import com.example.discount_service.repository.DiscountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final DiscountCourseRepository discountCourseRepository;
    private final DiscountMapper discountMapper;

    public DiscountResponseDTO createDiscount(DiscountRequestDTO request) {
        boolean hasSpecificCourses = request.getCourseIds() != null && !request.getCourseIds().isEmpty();
        Discount discount = discountMapper.toEntity(request);
        if(hasSpecificCourses) {
            discount.setAppliesToAllCourses(false);
        }
        discount = discountRepository.save(discount);
        if(hasSpecificCourses) {
            for(Long courseId : request.getCourseIds()) {
                DiscountCourse dc = new DiscountCourse();
                dc.setDiscount(discount);
                dc.setCourseId(courseId);
                discountCourseRepository.save(dc);
            }
        }
        
        DiscountResponseDTO response = discountMapper.toDTO(discount);
        return response;
    }

}
