package com.example.course_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_service.dto.ApiResponseDTO;
import com.example.course_service.dto.CustomPageDTO;
import com.example.course_service.dto.category.CategoryRequestDTO;
import com.example.course_service.dto.category.CategoryResponseDTO;
import com.example.course_service.service.category.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<CustomPageDTO<CategoryResponseDTO>>> getAllCategories(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        CustomPageDTO<CategoryResponseDTO> categories = categoryService.getAllCategories(pageable);
        ApiResponseDTO<CustomPageDTO<CategoryResponseDTO>> response = new ApiResponseDTO<>(200, categories,
                "Categories retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<CategoryResponseDTO>> createCategory(
            @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO category = categoryService.createCategory(categoryRequestDTO);
        ApiResponseDTO<CategoryResponseDTO> response = new ApiResponseDTO<>(HttpStatus.CREATED.value(), category,
                "Category created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<CategoryResponseDTO>> updateCatgory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO category = categoryService.updateCategory(id, categoryRequestDTO);
        ApiResponseDTO<CategoryResponseDTO> response = new ApiResponseDTO<>(HttpStatus.OK.value(), category,
                "Category updated successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        ApiResponseDTO<Void> response = new ApiResponseDTO<>(HttpStatus.OK.value(), null,
                "Category deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
