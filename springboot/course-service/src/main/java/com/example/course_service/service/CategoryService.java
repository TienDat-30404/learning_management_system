package com.example.course_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.course_service.dto.ApiResponseDTO;
import com.example.course_service.dto.category.CategoryRequestDTO;
import com.example.course_service.dto.category.CategoryResponseDTO;
import com.example.course_service.mapper.CategoryMapper;
import com.example.course_service.model.Category;
import com.example.course_service.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    public ApiResponseDTO<List<CategoryResponseDTO>> getAllCategories(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        List<CategoryResponseDTO> categories = categoryPage.stream()
                                                .map(categoryMapper::toDTO)
                                                .collect(Collectors.toList());
        return new ApiResponseDTO<>(200, categories, categoryPage.getTotalElements(), categoryPage.getTotalPages());
    }

    public ApiResponseDTO<CategoryResponseDTO> createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryMapper.toEntity(categoryRequestDTO);
        category = categoryRepository.save(category);
        CategoryResponseDTO categoryResponseDTO = categoryMapper.toDTO(category);
        return new ApiResponseDTO<>(201, categoryResponseDTO);
    }
}
