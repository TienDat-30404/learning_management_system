package com.example.course_service.service.category;
import org.springframework.data.domain.Pageable;
import com.example.course_service.dto.CustomPageDTO;
import com.example.course_service.dto.category.CategoryRequestDTO;
import com.example.course_service.dto.category.CategoryResponseDTO;


public interface CategoryService {
   
    public CustomPageDTO<CategoryResponseDTO> getAllCategories(Pageable pageable);

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO);

    public void deleteCategory(Long id);
}
