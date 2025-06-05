package com.example.enrollment_service.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.enrollment_service.client.CourseClient;
import com.example.enrollment_service.dto.ApiResponseDTO;
import com.example.enrollment_service.dto.CustomPageDTO;
import com.example.enrollment_service.dto.course.CourseResponseDTO;
import com.example.enrollment_service.dto.enrollment.EnrollmentRequestDTO;
import com.example.enrollment_service.dto.enrollment.EnrollmentResponseDTO;
import com.example.enrollment_service.mappper.EnrollmentMapper;
import com.example.enrollment_service.model.Enrollment;
import com.example.enrollment_service.repository.EnrollmentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final CourseClient courseClient;

    public Map<Long, CourseResponseDTO> fetchCourses(List<Long> courseIds) {
        try {

            ApiResponseDTO<List<CourseResponseDTO>> response = courseClient.getCourseByIds(courseIds);
            List<CourseResponseDTO> courses = response.getData();
            return courses.stream()
                    .filter(course -> course != null && course.getId() != null)
                    .collect(Collectors.toMap(CourseResponseDTO::getId, course -> course));
        } catch (Exception e) {
            log.error("Failed to fetch courses: {}", e.getMessage());
            return Map.of();
        }

    }

    public CustomPageDTO<EnrollmentResponseDTO> getAllEnrollments(Pageable pageable) {
        try {

            Page<Enrollment> enrollmentPage = enrollmentRepository.findAll(pageable);
            List<Enrollment> enrollments = enrollmentPage.getContent();
            List<Long> courseIds = enrollments.stream().map(Enrollment::getCourseId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            Map<Long, CourseResponseDTO> courseMap = fetchCourses(courseIds);
            System.out.println("idsCourse" + courseMap);
            List<EnrollmentResponseDTO> listEnrollments = enrollments.stream()
                    .map(enrollment -> {
                        EnrollmentResponseDTO dto = enrollmentMapper.toDTO(enrollment);
                        CourseResponseDTO course = courseMap.get(enrollment.getCourseId());
                        if (course != null) {
                            dto.setCourse(course);
                        }
                        return dto;
                    })
                    .collect(Collectors.toList());

            return new CustomPageDTO<>(
                    listEnrollments, enrollmentPage.getTotalElements(), enrollmentPage.getTotalPages());

        } catch (Exception e) {
            log.error("Error fetchin couses: {}", e.getMessage(), e);
            return new CustomPageDTO<>(List.of(), 0L, 0);
        }
    }

    public CustomPageDTO<EnrollmentResponseDTO> getAllEnrollmentByUser(Long userId, Pageable pageable) {
        try {

            Page<Enrollment> enrollmentPage = enrollmentRepository.findByUserId(userId, pageable);
            List<Enrollment> enrollments = enrollmentPage.getContent();
            List<Long> courseIds = enrollments.stream().map(Enrollment::getCourseId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            Map<Long, CourseResponseDTO> courseMap = fetchCourses(courseIds);

            List<EnrollmentResponseDTO> listEnrollments = enrollments.stream().map(enrollment -> {
                EnrollmentResponseDTO dto = enrollmentMapper.toDTO(enrollment);
                CourseResponseDTO course = courseMap.get(enrollment.getCourseId());
                if (course != null) {
                    dto.setCourse(course);
                }
                return dto;
            }).collect(Collectors.toList());

            return new CustomPageDTO<>(
                listEnrollments, enrollmentPage.getTotalElements(), enrollmentPage.getTotalPages()
            );
        } catch (Exception e) {
            log.error("Error fetchin couses: {}", e.getMessage(), e);
            return new CustomPageDTO<>(List.of(), 0L, 0);
        }

    }

    public void addEnrollment(EnrollmentRequestDTO request) {
        request.setProgress(Double.valueOf(0));
        Enrollment enrollment = enrollmentMapper.toEntity(request);
        enrollmentRepository.save(enrollment);
    }
}
