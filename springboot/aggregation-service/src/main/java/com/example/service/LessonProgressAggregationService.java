package com.example.service;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.example.dto.ApiResponseDTO;
import com.example.dto.CustomPageDTO;
import com.example.dto.LessonProgressDTO;
import com.example.dto.aggregation.LessonProgressAggregationDTO;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LessonProgressAggregationService {
        private final WebClient.Builder webClientBuilder;

        public Mono<CustomPageDTO<LessonProgressAggregationDTO>> getLessonProgressOfUser(int page, int size, Long courseId,
                        String token, String userId, String userRoles) {
                WebClient client = webClientBuilder.build();

                // 1. Gọi API lessons để lấy danh sách bài học
                Mono<ApiResponseDTO<CustomPageDTO<LessonProgressAggregationDTO>>> lessonsMono = client.get()
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
                                                new ParameterizedTypeReference<ApiResponseDTO<CustomPageDTO<LessonProgressAggregationDTO>>>() {
                                                });
                return lessonsMono.flatMap(lessonResponse -> {
                        CustomPageDTO<LessonProgressAggregationDTO> lessonsPage = lessonResponse.getData();
                        List<LessonProgressAggregationDTO> lessons = lessonsPage.getContent();

                        List<Long> lessonIds = lessons.stream()
                                        .map(LessonProgressAggregationDTO::getId)
                                        .distinct()
                                        .toList();

                        // 2. Gọi API lesson-progress với danh sách lessonIds
                        Mono<List<LessonProgressDTO>> lessonsProgressMono = client.get()
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
                                        .bodyToMono(new ParameterizedTypeReference<List<LessonProgressDTO>>() {
                                        });

                        // 3. Kết hợp dữ liệu từ lessons và lesson-progress
                        return lessonsProgressMono.map(progressList -> {
                                Map<Long, LessonProgressDTO> progressMap = progressList.stream()
                                                .collect(Collectors.toMap(
                                                                LessonProgressDTO::getLessonId,
                                                                progress -> progress,
                                                                (existing, replacement) -> existing));

                                lessons.forEach(lesson -> {
                                        LessonProgressDTO progress = progressMap.get(lesson.getId());
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
                                                lessonsPage.getTotalPages(),
                                                null
                                                );
                        });
                });
        }
}
