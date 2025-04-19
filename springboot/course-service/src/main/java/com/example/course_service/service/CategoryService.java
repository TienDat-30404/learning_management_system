package com.example.course_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.course_service.dto.ApiResponseDTO;
import com.example.course_service.dto.CustomPageDTO;
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

    @Cacheable(value = "categories", key = "'categories-page=' + #pageable.pageNumber + '$size=' + #pageable.pageSize")
    public CustomPageDTO<CategoryResponseDTO> getAllCategories(Pageable pageable) {
        Page<Category> categoPageryPage = categoryRepository.findAll(pageable);
        Page<CategoryResponseDTO> categories = categoPageryPage.map(categoryMapper::toDTO);
        return new CustomPageDTO<>(
                categories.getContent(),
                categories.getTotalElements(),
                categories.getTotalPages()
        );
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryMapper.toEntity(categoryRequestDTO);
        category = categoryRepository.save(category);
        CategoryResponseDTO response = categoryMapper.toDTO(category);
        return response;
    }
}
