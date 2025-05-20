package com.example.course_service.service.category;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.course_service.dto.CustomPageDTO;
import com.example.course_service.dto.category.CategoryRequestDTO;
import com.example.course_service.dto.category.CategoryResponseDTO;
import com.example.course_service.mapper.CategoryMapper;
import com.example.course_service.model.Category;
import com.example.course_service.repository.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    // @Cacheable(value = "categories", key = "'page=' + #pageable.pageNumber + '&size=' + #pageable.pageSize")
    public CustomPageDTO<CategoryResponseDTO> getAllCategories(Pageable pageable) {
        Page<Category> categoriesPage = categoryRepository.findAll(pageable);
        Page<CategoryResponseDTO> categories = categoriesPage.map(categoryMapper::toDTO);
        return new CustomPageDTO<>(
                categories.getContent(),
                categories.getTotalElements(),
                categories.getTotalPages());
    }

    // @CacheEvict(value = "categories", allEntries = true)
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryMapper.toEntity(categoryRequestDTO);
        category = categoryRepository.save(category);
        CategoryResponseDTO response = categoryMapper.toDTO(category);
        return response;
    }

    // @CacheEvict(value = "categories", allEntries = true)
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        category.setName(categoryRequestDTO.getName());
        Category upCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(upCategory);
    }


    // @CacheEvict(value = "categories", allEntries = true)
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(category);
    }
}
