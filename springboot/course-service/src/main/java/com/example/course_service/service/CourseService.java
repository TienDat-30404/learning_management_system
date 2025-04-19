package com.example.course_service.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import java.util.Collections;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.course_service.client.UserClient;
import com.example.course_service.dto.ApiResponseDTO;
import com.example.course_service.dto.CustomPageDTO;
import com.example.course_service.dto.course.CourseResponseDTO;
import com.example.course_service.dto.course.CourseResquestDTO;
import com.example.course_service.dto.user.UserResponseDTO;
import com.example.course_service.mapper.CourseMapper;
import com.example.course_service.model.Course;
import com.example.course_service.repository.CourseRepository;

import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserClient userClient;
    private final CourseMapper courseMapper;

    public CourseResponseDTO createCourse(CourseResquestDTO courseResquestDTO) {
        Course course = courseMapper.toEntity(courseResquestDTO);
        Long idUser = course.getUserId();
        Boolean userExists = userClient.checkExistUser(idUser);

        if (userExists == null || !userExists) {
            throw new IllegalArgumentException("User with id " + idUser + " does not exist");
        }

        course = courseRepository.save(course);
        CourseResponseDTO response = courseMapper.toDTO(course);
      
        return response;
    }

    public CustomPageDTO<CourseResponseDTO> getAllCourses(Pageable pageable) {
        try {

            Page<Course> coursePage = courseRepository.findAll(pageable);
            List<Course> courses = coursePage.getContent();
            List<Long> userIds = courses.stream()
                    .map(Course::getUserId)
                    .collect(Collectors.toList());

            Map<Long, UserResponseDTO> userMap = fetchUsers(userIds);
            List<CourseResponseDTO> coursesDTO = courses.stream().map(course -> {
                CourseResponseDTO dto = courseMapper.toDTO(course);
                UserResponseDTO user = userMap.get(course.getUserId());
                if (user != null) {
                    dto.setUser(user);
                }
                return dto;
            }).collect(Collectors.toList());

            return new CustomPageDTO<>(
                    coursesDTO,
                    coursePage.getTotalElements(),
                    coursePage.getTotalPages()
            );
        } catch (Exception e) {
            log.error("Error fetching courses: {}", e.getMessage(), e);
            return new CustomPageDTO<>(List.of(), 0L, 0);
        }
    }

    private Map<Long, UserResponseDTO> fetchUsers(List<Long> userIds) {
        try {
            ApiResponseDTO<List<UserResponseDTO>> response = userClient.getUsersByIds(userIds);

            List<UserResponseDTO> users = response.getData();
            System.out.println("users: " + users);
            return users.stream()
                    .filter(user -> user != null && user.getId() != null)
                    .collect(Collectors.toMap(UserResponseDTO::getId, user -> user));
        } catch (Exception e) {
            log.error("Failed to fetch users: {}", e.getMessage());
            return Map.of();
        }
    }

}
