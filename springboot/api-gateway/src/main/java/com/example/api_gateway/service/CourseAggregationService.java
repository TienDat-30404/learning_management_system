package com.example.api_gateway.service;

import com.example.api_gateway.dto.user.UserGatewayDTO;
import com.example.api_gateway.dto.ApiResponseDTO;
import com.example.api_gateway.dto.CustomPageDTO;
import com.example.api_gateway.dto.aggregated.AggregatedCourseResponse;
import com.example.api_gateway.dto.discount.DiscountGatewayResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class CourseAggregationService {

        private final WebClient.Builder webClientBuilder;

        // Gọi API courses, trả về Mono<CustomPageDTO<CourseGatewayDTO>>
        public Mono<CustomPageDTO<AggregatedCourseResponse>> getCoursesWithUsers(int page, int size) {
                WebClient client = webClientBuilder.build();

                // 1. Lấy courses
                Mono<ApiResponseDTO<CustomPageDTO<AggregatedCourseResponse>>> coursesMono = client.get()
                        .uri(uriBuilder -> uriBuilder
                                .scheme("http")
                                .host("course-service")
                                .path("/api/v1/courses")
                                .queryParam("page", page)
                                .queryParam("size", size)
                                .build())
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<CustomPageDTO<AggregatedCourseResponse>>>() {});

                return coursesMono.flatMap(courseResponse -> {
                        CustomPageDTO<AggregatedCourseResponse> coursesPage = courseResponse.getData();

                        List<AggregatedCourseResponse> courses = coursesPage.getContent();
                        List<Long> userIds = courses.stream()
                                        .map(AggregatedCourseResponse::getUserId)
                                        .distinct()
                                        .toList();

                        List<Long> courseIds = courses.stream()
                                        .map(AggregatedCourseResponse::getId)
                                        .distinct()
                                        .toList();

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

                        Mono<List<DiscountGatewayResponse>> discountsMono = client.get()
                                        .uri(uriBuilder -> {
                                                var builder = uriBuilder
                                                                .scheme("http")
                                                                .host("discount-service")
                                                                .path("/api/v1/discounts/get-discout-for-course");
                                                for (Long id : courseIds) {
                                                        builder.queryParam("courseIds", id);
                                                }
                                                return builder.build();
                                        })
                                        .retrieve()
                                        .bodyToMono(new ParameterizedTypeReference<List<DiscountGatewayResponse>>() {
                                        });

                        return usersMono.zipWith(discountsMono).map(tuple -> {
                                Map<Long, UserGatewayDTO> userMap = tuple.getT1().getData().stream()
                                                .collect(Collectors.toMap(UserGatewayDTO::getId, u -> u));
                                Map<Long, DiscountGatewayResponse> discountMap = tuple.getT2().stream()
                                                .collect(Collectors.toMap(DiscountGatewayResponse::getCourseId,
                                                                d -> d));

                                courses.forEach(course -> {
                                        course.setUser(userMap.get(course.getUserId()));
                                        course.setDiscount(discountMap.get(course.getId()));
                                });

                                return new CustomPageDTO<>(courses, coursesPage.getTotalElements(), coursesPage.getTotalPages());
                        });
                });
        }
}