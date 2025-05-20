package com.example.course_service.service.course;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import com.example.course_service.dto.CustomPageDTO;
import com.example.course_service.dto.course.CourseResponseDTO;
import com.example.course_service.dto.course.CourseResquestDTO;
import com.example.course_service.dto.course.CourseUpdatetDTO;
import com.example.course_service.dto.user.UserResponseDTO;


public interface CourseService {
   
    public CourseResponseDTO createCourse(CourseResquestDTO courseResquestDTO);

    // @Cacheable(value = "courses", key = "'page=' + #pageable.pageNumber + '&size=' + #pageable.pageSize")
    public CustomPageDTO<CourseResponseDTO> getAllCourses(Pageable pageable);

    public Map<Long, UserResponseDTO> fetchUsers(List<Long> userIds);


    public CourseResponseDTO updateCourse(Long id, CourseUpdatetDTO request);

}
