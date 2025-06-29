package com.example.course_service.service.course;

import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.course_service.client.UserClient;
import com.example.course_service.dto.ApiResponseDTO;
import com.example.course_service.dto.CustomPageDTO;
import com.example.course_service.dto.course.CourseResponseDTO;
import com.example.course_service.dto.course.CourseResquestDTO;
import com.example.course_service.dto.course.CourseUpdatetDTO;
import com.example.course_service.dto.user.UserResponseDTO;
import com.example.course_service.mapper.CourseMapper;
import com.example.course_service.model.Course;
import com.example.course_service.model.Category;

import com.example.course_service.repository.CategoryRepository;
import com.example.course_service.repository.CourseRepository;
import com.example.course_service.service.CloudinaryService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserClient userClient;
    private final CourseMapper courseMapper;
    private final CloudinaryService cloudinaryService;
    private final CategoryRepository categoryRepository;

    // @CacheEvict(value = "courses", allEntries = true)
    public CourseResponseDTO createCourse(CourseResquestDTO courseResquestDTO) {
        Long userId = courseResquestDTO.getUserId();
        Boolean userExists = userClient.checkExistUser(userId);
        if (userExists == null || !userExists) {
            throw new IllegalArgumentException("User with id " + userId + " does not exist");
        }
  

        String imageUrl = null;

        if (courseResquestDTO.getImage() != null && !courseResquestDTO.getImage().isEmpty()) {
            try {
                imageUrl = cloudinaryService.uploadImage(courseResquestDTO.getImage());
            } catch (IOException e) {
                throw new RuntimeException("Upload ảnh thất bại: " + e.getMessage());
            }
        }

        Category category = categoryRepository.findById(courseResquestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Category not found with id: " + courseResquestDTO.getCategoryId()));
        Course course = courseMapper.toEntity(courseResquestDTO);
        course.setUserId(userId);
        course.setImage(imageUrl);
        course.setCategory(category);
        course = courseRepository.save(course);

        CourseResponseDTO response = courseMapper.toDTO(course);
        return response;
    }

    // @Cacheable(value = "courses", key = "'page=' + #pageable.pageNumber +
    // '&size=' + #pageable.pageSize")
    public CustomPageDTO<CourseResponseDTO> getAllCourses(Pageable pageable) {
        try {

            Page<Course> coursePage = courseRepository.findAll(pageable);
            Page<CourseResponseDTO> courses = coursePage.map(courseMapper::toDTO);
 
            return new CustomPageDTO<>(
                    courses.getContent(),
                    coursePage.getTotalElements(),
                    coursePage.getTotalPages());
        } catch (Exception e) {
            log.error("Error fetchin couses: {}", e.getMessage(), e);
            return new CustomPageDTO<>(List.of(), 0L, 0);
        }
    }

   

    public CourseResponseDTO updateCourse(Long id, CourseUpdatetDTO request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            try {
                String imageurl = cloudinaryService.uploadImage(request.getImage());
                course.setImage(imageurl);
            } catch (IOException e) {
                throw new RuntimeException("Upload ảnh thất bại: " + e.getMessage());
            }
        }
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(
                            () -> new EntityNotFoundException("Category not found with id: " +
                                    request.getCategoryId()));
            course.setCategory(category);
        }

        courseMapper.updateCourseFromDTO(request, course);
        Course updatedCourse = courseRepository.save(course);
        // ApiResponseDTO<UserResponseDTO> userResponse = userClient.getUserById(updatedCourse.getUserId());
        CourseResponseDTO responseDTO = courseMapper.toDTO(updatedCourse);
        // responseDTO.setUser(userResponse.getData());

        return responseDTO;
    }

    public CourseResponseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id=" + id));
        return courseMapper.toDTO(course);
    }

    public List<CourseResponseDTO> getCourseByIds(List<Long> ids) {
        List<Course> courses = courseRepository.findAllById(ids);
        List<CourseResponseDTO> response = courses.stream().map(courseMapper::toDTO)
                .collect(Collectors.toList());
        return response;
    }

       public boolean existsById(Long id) {
        return courseRepository.existsById(id);
    }

}
