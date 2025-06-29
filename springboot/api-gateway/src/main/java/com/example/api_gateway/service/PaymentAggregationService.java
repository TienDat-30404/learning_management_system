package com.example.api_gateway.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.api_gateway.dto.ApiResponseDTO;
import com.example.api_gateway.dto.CustomPageDTO;
import com.example.api_gateway.dto.aggregated.AggreatedPaymentResponse;
import com.example.api_gateway.dto.course.CourseGatewayDTO;
import com.example.api_gateway.dto.payment.PaymentRequest;
import com.example.api_gateway.dto.user.UserGatewayDTO;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentAggregationService {
        private final WebClient.Builder webClientBuilder;

        public Mono<CustomPageDTO<AggreatedPaymentResponse>> handleImplementPayment(String token, PaymentRequest paymentRequest) {
                WebClient client = webClientBuilder.build();

                // 1. Gọi payment-service để lấy danh sách enrollments bằng POST
                Mono<ApiResponseDTO<AggreatedPaymentResponse>> paymentMono = client.post()
                        .uri("http://payment-service/api/v1/payments")
                        .header("Authorization", token)
                        .bodyValue(paymentRequest)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<AggreatedPaymentResponse>>() {});

                return paymentMono.flatMap(paymentResponse -> {
                        AggreatedPaymentResponse payment = paymentResponse.getData();
                        System.out.println("paymenttttttttttttt" + payment);
                        Long userId = payment.getUserId();
                        System.out.println("userIdddddđ" + userId);
                        Long courseId = payment.getCourseId();
                        
                        Mono<ApiResponseDTO<UserGatewayDTO>> userMono = client.get()
                                .uri("http://user-service/api/v1/users/{userId}", userId)
                                .header("Authorization", token)
                                .retrieve()
                                .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<UserGatewayDTO>>() {
                                });

                        Mono<ApiResponseDTO<CourseGatewayDTO>> courseMono = client.get()
                                .uri("http://course-service/api/v1/courses/{courseId}", courseId)
                                .retrieve()
                                .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<CourseGatewayDTO>>() {
                                });

                        return Mono.zip(userMono, courseMono)
                                .map(tuple -> {
                                        UserGatewayDTO user = tuple.getT1().getData();
                                        CourseGatewayDTO course = tuple.getT2().getData();

                                        payment.setUser(user);
                                        payment.setCourse(course);

                                        // Vì CustomPageDTO yêu cầu danh sách, nên trả về danh sách chứa 1 phần tử
                                        return new CustomPageDTO<>(
                                                List.of(payment),
                                                1L,
                                                1
                                        );
                                });

                });
        }

}
