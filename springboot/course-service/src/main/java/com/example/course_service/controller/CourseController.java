package com.example.course_service.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.http44.api.Response;
import com.example.course_service.client.UserClient;
import com.example.course_service.dto.ApiResponseDTO;
import com.example.course_service.dto.CustomPageDTO;
import com.example.course_service.dto.category.CategoryResponseDTO;
import com.example.course_service.dto.course.CourseResponseDTO;
import com.example.course_service.dto.course.CourseResquestDTO;
import com.example.course_service.dto.course.CourseUpdatetDTO;
import com.example.course_service.dto.user.UserResponseDTO;
import com.example.course_service.model.Course;
import com.example.course_service.service.course.CourseService;

import jakarta.validation.Valid;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity<ApiResponseDTO<CourseResponseDTO>> createCourse(
            @ModelAttribute @Valid CourseResquestDTO courseRequestDTO) {
        CourseResponseDTO course = courseService.createCourse(courseRequestDTO);
        ApiResponseDTO<CourseResponseDTO> response = new ApiResponseDTO<>(
                HttpStatus.CREATED.value(), course, "Course created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<CustomPageDTO<CourseResponseDTO>>> getAllCourses(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        CustomPageDTO<CourseResponseDTO> courses = courseService.getAllCourses(pageable);
        ApiResponseDTO<CustomPageDTO<CourseResponseDTO>> response = new ApiResponseDTO<>(200, courses,
                "Courses retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<CourseResponseDTO>> updateCourse(
            @PathVariable("id") Long id,
            @ModelAttribute @Valid CourseUpdatetDTO request) {
        CourseResponseDTO courseResponseDTO = courseService.updateCourse(id, request);
        ApiResponseDTO<CourseResponseDTO> response = new ApiResponseDTO<>(
                HttpStatus.OK.value(), courseResponseDTO, "Course updated successfully");
        return ResponseEntity.ok(response);
    }

}
