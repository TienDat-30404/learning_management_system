package com.example.course_service.mapper;

import org.springframework.stereotype.Component;

import com.example.course_service.dto.category.CategoryResponseDTO;
import com.example.course_service.dto.course.CourseResponseDTO;
import com.example.course_service.dto.course.CourseResquestDTO;
import com.example.course_service.dto.course.CourseUpdatetDTO;
import com.example.course_service.dto.user.UserResponseDTO;
import com.example.course_service.model.Course;

import lombok.Data;

@Data
@Component
public class CourseMapper {
    private final CategoryMapper categoryMapper;
    public CourseResponseDTO toDTO(Course course) {
        CourseResponseDTO dto = new CourseResponseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setImage(course.getImage());

        CategoryResponseDTO category = categoryMapper.toDTO(course.getCategory());
        dto.setCategory(category);


        dto.setPrice(course.getPrice());
        return dto;
    }

    public Course toEntity(CourseResquestDTO courseResquestDTO) {
        Course course = new Course();
        course.setTitle(courseResquestDTO.getTitle());
        course.setDescription(courseResquestDTO.getDescription());
        course.setUserId(courseResquestDTO.getUserId());
        course.setPrice(courseResquestDTO.getPrice());
        return course;
    }

    
    public void updateCourseFromDTO(CourseUpdatetDTO dto, Course course) {
        if (dto.getTitle() != null) course.setTitle(dto.getTitle());
        if (dto.getDescription() != null) course.setDescription(dto.getDescription());
        if(dto.getUserId() != null) course.setUserId(dto.getUserId());
        if (dto.getPrice() != null) course.setPrice(dto.getPrice());
    }
}
