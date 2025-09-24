package com.example.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import org.springframework.web.server.ServerWebExchange;

import com.example.dto.ApiResponseDTO;
import com.example.dto.CourseDTO;
import com.example.dto.CustomPageDTO;
import com.example.dto.UserDTO;
import com.example.dto.aggregation.EnrollmentAggregationDTO;

@Service
@RequiredArgsConstructor
public class EnrollmentAggregationService {
        private final WebClient.Builder webClientBuilder;

        public Mono<CustomPageDTO<EnrollmentAggregationDTO>> getEnrollmentsWithCoursesAndUsers(String token,
                        String userId, String userRoles) {
                WebClient client = webClientBuilder.build();

                // 1. Lấy enrollments
                Mono<ApiResponseDTO<CustomPageDTO<EnrollmentAggregationDTO>>> enrollmentsMono = client.get()
                                .uri("lb://enrollment-service/api/v1/enrollments")
                                .header("Authorization", token)
                                .header("X-User-Roles", userRoles)
                                .header("X-User-ID", userId)
                                .retrieve()

                                .bodyToMono(
                                                new ParameterizedTypeReference<ApiResponseDTO<CustomPageDTO<EnrollmentAggregationDTO>>>() {
                                                });

                return enrollmentsMono.flatMap(enrollmentResponse -> {
                        CustomPageDTO<EnrollmentAggregationDTO> enrollmentPage = enrollmentResponse.getData();
                        List<EnrollmentAggregationDTO> enrollments = enrollmentPage.getContent();
                        // Lấy danh sách courseIds và userIds trong enrollments
                        List<Long> courseIds = enrollments.stream()
                                        .map(EnrollmentAggregationDTO::getCourseId)
                                        .distinct()
                                        .toList();

                        List<Long> userIds = enrollments.stream()
                                        .map(EnrollmentAggregationDTO::getUserId) // Giả sử enrollment có
                                                                                  // getUserId()
                                        .distinct()
                                        .toList();

                        // 2. Gọi course-service lấy courses đã gộp user
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

                        // 3. Gọi user-service lấy users
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

                        // 4. Kết hợp kết quả courses và users
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

                                                // Gán course và user vào enrollment
                                                enrollments.forEach(enrollment -> {
                                                        CourseDTO course = courseMap
                                                                        .get(enrollment.getCourseId());
                                                        if (course != null) {
                                                                enrollment.setCourse(course);
                                                        }

                                                        UserDTO user = userMap.get(enrollment.getUserId());
                                                        if (user != null) {
                                                                enrollment.setUser(user); // Bạn cần thêm phương thức
                                                                                          // setUser() trong
                                                                                          // AggregatedEnrollmentResponse
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
        }

}
