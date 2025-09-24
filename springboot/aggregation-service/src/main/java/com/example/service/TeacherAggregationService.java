package com.example.service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.dto.ApiResponseDTO;
import com.example.dto.CourseDTO;
import com.example.dto.CustomPageDTO;
import com.example.dto.UserDTO;
import com.example.dto.aggregation.EnrollmentAggregationDTO;
import com.example.dto.aggregation.teacher.DashboardAggregationDTO;
import com.example.dto.aggregation.teacher.StudentAggregationDTO;

@Service
@RequiredArgsConstructor
public class TeacherAggregationService {

        private final WebClient.Builder webClientBuilder;

        public Mono<CustomPageDTO<DashboardAggregationDTO>> getCoursesWithUsers(int page, int size, String token,
                        String userId, String userRoles) {
                WebClient client = webClientBuilder.build();

                // 1. Gọi API courses để lấy danh sách khóa học và thông tin cơ bản
                Mono<ApiResponseDTO<CustomPageDTO<DashboardAggregationDTO>>> coursesMono = client.get()
                                .uri(uriBuilder -> uriBuilder
                                                .scheme("http")
                                                .host("course-service")
                                                .path("/api/v1/courses/courses-by-user")
                                                .queryParam("page", page)
                                                .queryParam("size", size)
                                                .build())
                                .header("Authorization", token)
                                .header("X-User-Roles", userRoles)
                                .header("X-User-ID", userId)
                                .retrieve()
                                .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<CustomPageDTO<DashboardAggregationDTO>>>() {
                                });

                // Sử dụng flatMap để xử lý kết quả từ Mono đầu tiên
                return coursesMono.flatMap(coursesResponse -> {
                        CustomPageDTO<DashboardAggregationDTO> coursesPage = coursesResponse.getData();
                        List<DashboardAggregationDTO> courses = coursesPage.getContent();

                        List<Long> courseIds = courses.stream()
                                        .map(DashboardAggregationDTO::getId)
                                        .distinct()
                                        .toList();
                        System.out.println("courseIdssss" + courseIds);
                        if (courseIds.isEmpty()) {
                                return Mono.just(new CustomPageDTO<>(new ArrayList<>(), 0L, 0, 200L));
                        }

                        Mono<ApiResponseDTO<List<Long>>> lessonsMono = client.get()
                                        .uri(UriComponentsBuilder.newInstance()
                                                        .scheme("http")
                                                        .host("course-service")
                                                        .path("/api/v1/lessons/ids-by-courses")
                                                        .queryParam("ids", courseIds.toArray())
                                                        .build()
                                                        .toUri())
                                        .header("Authorization", token)
                                        .header("X-User-Roles", userRoles)
                                        .header("X-User-ID", userId)
                                        .retrieve()
                                        .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<List<Long>>>() {
                                        });

                        Mono<ApiResponseDTO<Long>> totalQuizzesMono = lessonsMono.flatMap(lessonsResponse -> {
                                List<Long> lessonIds = lessonsResponse.getData();
                                if (lessonIds == null || lessonIds.isEmpty()) {
                                        return Mono.just(new ApiResponseDTO<>(200, 0L, "No quizzes found"));
                                }

                                // Gọi API quiz bằng danh sách lessonId và trả về Mono từ cuộc gọi đó
                                return client.get()
                                                .uri(UriComponentsBuilder.newInstance()
                                                                .scheme("http")
                                                                .host("quiz-service")
                                                                .path("/api/v1/quizs/total-quiz-of-courses")
                                                                .queryParam("lessonId", lessonIds.toArray())
                                                                .build()
                                                                .toUri())
                                                .header("Authorization", token)
                                                .header("X-User-Roles", userRoles)
                                                .header("X-User-ID", userId)
                                                .retrieve()
                                                .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<Long>>() {
                                                });
                        });

                        // 2. Gọi API enrollments để lấy số lượng học viên cho mỗi khóa học
                        Mono<ApiResponseDTO<List<Long>>> studentCountsMono = client.get()
                                        .uri(UriComponentsBuilder.newInstance()
                                                        .scheme("http")
                                                        .host("enrollment-service")
                                                        .path("/api/v1/enrollments/get-student-counts")
                                                        .queryParam("ids", courseIds.toArray())
                                                        .build()
                                                        .toUri())
                                        .header("Authorization", token)
                                        .header("X-User-Roles", userRoles)
                                        .header("X-User-ID", userId)
                                        .retrieve()
                                        .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<List<Long>>>() {
                                        });

                        // 3. Kết hợp các kết quả từ các Mono
                        return Mono.zip(Mono.just(courses), studentCountsMono, totalQuizzesMono)
                                        .map(tuple -> {
                                                List<DashboardAggregationDTO> coursesResult = tuple.getT1();
                                                List<Long> studentCounts = tuple.getT2().getData();
                                                Long totalQuizzes = tuple.getT3().getData();
                                                // Ánh xạ số lượng sinh viên vào các khóa học
                                                Map<Long, Long> studentCountMap;
                                                if (studentCounts == null || studentCounts.isEmpty()) {
                                                        studentCountMap = coursesResult.stream()
                                                                        .collect(Collectors.toMap(
                                                                                        DashboardAggregationDTO::getId,
                                                                                        course -> 0L));
                                                } else {
                                                        // Ánh xạ bình thường nếu có dữ liệu
                                                        studentCountMap = IntStream.range(0, courseIds.size())
                                                                        .boxed()
                                                                        .collect(Collectors.toMap(courseIds::get,
                                                                                        studentCounts::get));
                                                }

                                                // Tạo danh sách DTO tổng hợp cuối cùng
                                                List<DashboardAggregationDTO> aggregatedCourses = coursesResult.stream()
                                                                .map(course -> {
                                                                        DashboardAggregationDTO aggregatedDto = new DashboardAggregationDTO();

                                                                        // Sao chép tất cả các thuộc tính từ 'course'
                                                                        // sang 'aggregatedDto'
                                                                        BeanUtils.copyProperties(course, aggregatedDto);

                                                                        aggregatedDto.setQuantityStudent(studentCountMap
                                                                                        .getOrDefault(course.getId(),
                                                                                                        0L));

                                                                        return aggregatedDto;
                                                                })
                                                                .toList();

                                                return new CustomPageDTO<>(aggregatedCourses,
                                                                coursesPage.getTotalElements(),
                                                                coursesPage.getTotalPages(),
                                                                totalQuizzes);
                                        });
                });
        }

        public Mono<CustomPageDTO<StudentAggregationDTO>> getAllStudentOfTeacher(int page, int size, String token,
                        String userId, String userRoles, Long studentId) {
                WebClient client = webClientBuilder.build();

                // 1. Gọi API courses để lấy danh sách khóa học và thông tin cơ bản
                Mono<ApiResponseDTO<CustomPageDTO<DashboardAggregationDTO>>> coursesMonoByTeacher = client.get()
                                .uri(uriBuilder -> uriBuilder
                                                .scheme("http")
                                                .host("course-service")
                                                .path("/api/v1/courses/courses-by-user")
                                                .build())
                                .header("Authorization", token)
                                .header("X-User-Roles", userRoles)
                                .header("X-User-ID", userId)
                                .retrieve()
                                .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<CustomPageDTO<DashboardAggregationDTO>>>() {
                                });

                // Sử dụng flatMap để xử lý kết quả từ Mono đầu tiên
                return coursesMonoByTeacher.flatMap(coursesResponse -> {
                        CustomPageDTO<DashboardAggregationDTO> coursesPage = coursesResponse.getData();
                        List<DashboardAggregationDTO> coursesByTeacher = coursesPage.getContent();

                        List<Long> courseIds = coursesByTeacher.stream()
                                        .map(DashboardAggregationDTO::getId)
                                        .distinct()
                                        .toList();

                        if (courseIds.isEmpty()) {
                                return Mono.just(new CustomPageDTO<>(new ArrayList<>(), 0L, 0, null));
                        }

                        Mono<ApiResponseDTO<CustomPageDTO<StudentAggregationDTO>>> enrollmentsMono = client.get()
                                        .uri(UriComponentsBuilder.newInstance()
                                                        .scheme("http")
                                                        .host("enrollment-service")
                                                        .path("/api/v1/enrollments/all-student-by-courses")
                                                        .queryParam("courseId", courseIds.toArray())
                                                        .queryParam("page", page)
                                                        .queryParam("size", size)
                                                        .queryParam("userId", studentId)
                                                        .build()
                                                        .toUri())
                                        .header("Authorization", token)
                                        .header("X-User-Roles", userRoles)
                                        .header("X-User-ID", userId)
                                        .retrieve()
                                        .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<CustomPageDTO<StudentAggregationDTO>>>() {
                                        });

                        return enrollmentsMono.flatMap(enrollmentResponse -> {
                                CustomPageDTO<StudentAggregationDTO> enrollmentPage = enrollmentResponse.getData();
                                List<StudentAggregationDTO> enrollments = enrollmentPage.getContent();

                                List<Long> userIds = enrollments.stream()
                                                .map(StudentAggregationDTO::getUserId)
                                                .distinct()
                                                .toList();

                                if (userIds.isEmpty()) {
                                        return Mono.just(new CustomPageDTO<>(new ArrayList<>(), 0L, 0, null));
                                }

                                List<Long> courseIdsOfEnrollment = enrollments.stream()
                                                .map(StudentAggregationDTO::getCourseId)
                                                .distinct()
                                                .toList();

                                Mono<ApiResponseDTO<List<UserDTO>>> usersMono = client.get()
                                                .uri(uriBuilder -> {
                                                        var builder = uriBuilder
                                                                        .scheme("http")
                                                                        .host("user-service")
                                                                        .path("/api/v1/users/by-ids");
                                                        for (Long id : userIds) {
                                                                builder.queryParam("ids", id);
                                                        }
                                                        return builder.build();
                                                })
                                                .retrieve()
                                                .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<List<UserDTO>>>() {
                                                });

                                Mono<ApiResponseDTO<List<CourseDTO>>> coursesMono = client.get()
                                                .uri(uriBuilder -> {
                                                        var builder = uriBuilder
                                                                        .scheme("http")
                                                                        .host("course-service")
                                                                        .path("/api/v1/courses/by-ids");
                                                        for (Long id : courseIds) {
                                                                builder.queryParam("ids", id);
                                                        }
                                                        return builder.build();
                                                })
                                                .retrieve()
                                                .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<List<CourseDTO>>>() {
                                                });

                                return Mono.zip(coursesMono, usersMono)
                                                .map(tuple -> {
                                                        List<CourseDTO> courses = tuple.getT1().getData();
                                                        List<UserDTO> users = tuple.getT2().getData();

                                                        Map<Long, CourseDTO> courseMap = courses.stream()
                                                                        .collect(Collectors.toMap(CourseDTO::getId,
                                                                                        course -> course));

                                                        Map<Long, UserDTO> userMap = users.stream()
                                                                        .collect(Collectors.toMap(UserDTO::getId,
                                                                                        user -> user));
                                                        enrollments.forEach(enrollment -> {
                                                                CourseDTO course = courseMap
                                                                                .get(enrollment.getCourseId());
                                                                if (course != null) {
                                                                        enrollment.setCourse(course);
                                                                }

                                                                UserDTO user = userMap.get(enrollment.getUserId());
                                                                if (user != null) {
                                                                        enrollment.setUser(user);
                                                                }
                                                        });

                                                        // Trả về enrollments kèm course và user
                                                        return new CustomPageDTO<>(
                                                                        enrollments,
                                                                        enrollmentPage.getTotalElements(),
                                                                        enrollmentPage.getTotalPages(),
                                                                        null);
                                                });

                        });

                        // 3. Kết hợp các kết quả từ các Mono
                });
        }
}
