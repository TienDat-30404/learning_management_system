package com.example.course_service.mapper;

import org.springframework.stereotype.Component;

import com.example.course_service.dto.course.CourseResponseDTO;
import com.example.course_service.dto.course.CourseResquestDTO;
import com.example.course_service.model.Category;
import com.example.course_service.model.Course;
import com.example.course_service.repository.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;

@Data
@Component
public class CourseMapper {
    private final CategoryRepository categoryRepository;
    public CourseResponseDTO toDTO(Course course) {
        CourseResponseDTO dto = new CourseResponseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setImage(course.getImage());
        dto.setCategory(course.getCategory());
        dto.setPrice(course.getPrice());
        return dto;
    }

    public Course toEntity(CourseResquestDTO courseResquestDTO) {
        Course course = new Course();
        course.setTitle(courseResquestDTO.getTitle());
        course.setDescription(courseResquestDTO.getDescription());
        course.setImage(courseResquestDTO.getImage());
        Category category = categoryRepository.findById(courseResquestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + courseResquestDTO.getCategoryId()));
        course.setCategory(category);
        course.setUserId(courseResquestDTO.getUserId());
        course.setPrice(courseResquestDTO.getPrice());
        return course;
    }
}
