package com.example.api_gateway.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.api_gateway.dto.ApiResponseDTO;
import com.example.api_gateway.dto.CustomPageDTO;
import com.example.api_gateway.dto.aggregated.AggregatedEnrollmentResponse;
import com.example.api_gateway.dto.course.CourseGatewayDTO;
import com.example.api_gateway.dto.user.UserGatewayDTO;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import org.springframework.web.server.ServerWebExchange; 

@Service
@RequiredArgsConstructor
public class EnrollmentAggregationService {
        private final WebClient.Builder webClientBuilder;

        public Mono<CustomPageDTO<AggregatedEnrollmentResponse>> getEnrollmentsWithCoursesAndUsers(String token, ServerWebExchange exchange) {
                WebClient client = webClientBuilder.build();
                 String userId = exchange.getRequest().getHeaders().getFirst("X-User-ID");
                String userRoles = exchange.getRequest().getHeaders().getFirst("X-User-Roles");

                // 1. Lấy enrollments
                Mono<ApiResponseDTO<CustomPageDTO<AggregatedEnrollmentResponse>>> enrollmentsMono = client.get()
                                .uri("lb://enrollment-service/api/v1/enrollments")
                                .header("Authorization", token)
                                .header("X-User-Roles", userRoles)
                                .header("X-User-ID", userId)
                                .retrieve()

                                .bodyToMono(
                                                new ParameterizedTypeReference<ApiResponseDTO<CustomPageDTO<AggregatedEnrollmentResponse>>>() {
                                                });
                return enrollmentsMono.flatMap(enrollmentResponse -> {
                        CustomPageDTO<AggregatedEnrollmentResponse> enrollmentPage = enrollmentResponse.getData();
                        List<AggregatedEnrollmentResponse> enrollments = enrollmentPage.getContent();

                        // Lấy danh sách courseIds và userIds trong enrollments
                        List<Long> courseIds = enrollments.stream()
                                        .map(AggregatedEnrollmentResponse::getCourseId)
                                        .distinct()
                                        .toList();

                        List<Long> userIds = enrollments.stream()
                                        .map(AggregatedEnrollmentResponse::getUserId) // Giả sử enrollment có
                                                                                      // getUserId()
                                        .distinct()
                                        .toList();

                        // 2. Gọi course-service lấy courses đã gộp user
                        Mono<ApiResponseDTO<List<CourseGatewayDTO>>> coursesMono = client.get()
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
                                        .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<List<CourseGatewayDTO>>>() {
                                        });

                        // 3. Gọi user-service lấy users
                        Mono<ApiResponseDTO<List<UserGatewayDTO>>> usersMono = client.get()
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
                                        .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<List<UserGatewayDTO>>>() {
                                        });

                        // 4. Kết hợp kết quả courses và users
                        return Mono.zip(coursesMono, usersMono)
                                        .map(tuple -> {
                                                List<CourseGatewayDTO> courses = tuple.getT1().getData();
                                                List<UserGatewayDTO> users = tuple.getT2().getData();

                                                Map<Long, CourseGatewayDTO> courseMap = courses.stream()
                                                                .collect(Collectors.toMap(CourseGatewayDTO::getId,
                                                                                course -> course));

                                                Map<Long, UserGatewayDTO> userMap = users.stream()
                                                                .collect(Collectors.toMap(UserGatewayDTO::getId,
                                                                                user -> user));

                                                // Gán course và user vào enrollment
                                                enrollments.forEach(enrollment -> {
                                                        CourseGatewayDTO course = courseMap
                                                                        .get(enrollment.getCourseId());
                                                        if (course != null) {
                                                                enrollment.setCourse(course);
                                                        }

                                                        UserGatewayDTO user = userMap.get(enrollment.getUserId());
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
                                                                enrollmentPage.getTotalPages());
                                        });
                });
        }

}
