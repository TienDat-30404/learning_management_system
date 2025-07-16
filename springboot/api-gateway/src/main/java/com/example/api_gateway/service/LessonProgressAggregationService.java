package com.example.api_gateway.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.example.api_gateway.dto.ApiResponseDTO;
import com.example.api_gateway.dto.CustomPageDTO;
import com.example.api_gateway.dto.aggregated.AggregatedCourseResponse;
import com.example.api_gateway.dto.aggregated.AggregatedEnrollmentResponse;
import com.example.api_gateway.dto.aggregated.AggregatedLessonProgressResponse;
import com.example.api_gateway.dto.lesson_progress.LessonProgressGatewayResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LessonProgressAggregationService {
        private final WebClient.Builder webClientBuilder;

        public Mono<CustomPageDTO<AggregatedLessonProgressResponse>> getLessonProgressOfUser(int page, int size, Long courseId,
                        String token, ServerWebExchange exchange) {
                WebClient client = webClientBuilder.build();

                String userId = exchange.getRequest().getHeaders().getFirst("X-User-ID");
                String userRoles = exchange.getRequest().getHeaders().getFirst("X-User-Roles");
                // 1. Gọi API lessons để lấy danh sách bài học
                Mono<ApiResponseDTO<CustomPageDTO<AggregatedLessonProgressResponse>>> lessonsMono = client.get()
                                .uri(uriBuilder -> uriBuilder
                                                .scheme("http")
                                                .host("course-service")
                                                .path("/api/v1/lessons")
                                                .queryParam("courseId", courseId)
                                                .queryParam("page", page)
                                                .queryParam("size", size)
                                                .build())
                                .header("Authorization", token)
                                .header("X-User-Roles", userRoles)
                                .header("X-User-ID", userId)
                                .retrieve()
                                .bodyToMono(
                                                new ParameterizedTypeReference<ApiResponseDTO<CustomPageDTO<AggregatedLessonProgressResponse>>>() {
                                                });
                return lessonsMono.flatMap(lessonResponse -> {
                        CustomPageDTO<AggregatedLessonProgressResponse> lessonsPage = lessonResponse.getData();
                        System.out.println("lessonPage" + lessonsPage);
                        List<AggregatedLessonProgressResponse> lessons = lessonsPage.getContent();

                        List<Long> lessonIds = lessons.stream()
                                        .map(AggregatedLessonProgressResponse::getId)
                                        .distinct()
                                        .toList();

                        // 2. Gọi API lesson-progress với danh sách lessonIds
                        Mono<List<LessonProgressGatewayResponse>> lessonsProgressMono = client.get()
                                        .uri(uriBuilder -> {
                                                var builder = uriBuilder
                                                                .scheme("http")
                                                                .host("enrollment-service")
                                                                .path("/api/v1/lesson-progress/get-lesson-completed");
                                                for (Long id : lessonIds) {
                                                        builder.queryParam("lessonIds", id);
                                                }
                                                return builder.build();
                                        })
                                        .header("Authorization", token)
                                        .header("X-User-Roles", userRoles)
                                        .header("X-User-ID", userId)
                                        .retrieve()
                                        .bodyToMono(new ParameterizedTypeReference<List<LessonProgressGatewayResponse>>() {
                                        });

                        // 3. Kết hợp dữ liệu từ lessons và lesson-progress
                        return lessonsProgressMono.map(progressList -> {
                                Map<Long, LessonProgressGatewayResponse> progressMap = progressList.stream()
                                                .collect(Collectors.toMap(
                                                                LessonProgressGatewayResponse::getLessonId,
                                                                progress -> progress,
                                                                (existing, replacement) -> existing));

                                lessons.forEach(lesson -> {
                                        LessonProgressGatewayResponse progress = progressMap.get(lesson.getId());
                                        if (progress != null) {
                                                Boolean checkCompleted = progress.getCompleted();
                                                lesson.setIsCompleted(checkCompleted);
                                        } else {
                                                lesson.setIsCompleted(false); 
                                        }
                                });

                                return new CustomPageDTO<>(
                                                lessons,
                                                lessonsPage.getTotalElements(),
                                                lessonsPage.getTotalPages());
                        });
                });
        }
}