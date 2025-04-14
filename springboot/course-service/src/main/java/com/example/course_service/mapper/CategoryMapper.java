package com.example.course_service.mapper;

import com.example.course_service.model.Category;

import org.springframework.stereotype.Component;

import com.example.course_service.dto.category.CategoryRequestDTO;
import com.example.course_service.dto.category.CategoryResponseDTO;

@Component
public class CategoryMapper {
    public CategoryResponseDTO toDTO (Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    public Category toEntity(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        return category;
    }
}
