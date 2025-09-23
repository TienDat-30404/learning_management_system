package com.example.service;



import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.dto.ApiResponseDTO;
import com.example.dto.CustomPageDTO;
import com.example.dto.DiscountDTO;
import com.example.dto.UserDTO;
import com.example.dto.aggregation.CourseAggregationDTO;

@Service
@RequiredArgsConstructor
public class CourseAggregationService {

        private final WebClient.Builder webClientBuilder;

        // Gọi API courses, trả về Mono<CustomPageDTO<CourseGatewayDTO>>
        public Mono<CustomPageDTO<CourseAggregationDTO>> getCoursesWithUsers(int page, int size) {
                WebClient client = webClientBuilder.build();

                // 1. Lấy courses
                Mono<ApiResponseDTO<CustomPageDTO<CourseAggregationDTO>>> coursesMono = client.get()
                                .uri(uriBuilder -> uriBuilder
                                                .scheme("http")
                                                .host("course-service")
                                                .path("/api/v1/courses")
                                                .queryParam("page", page)
                                                .queryParam("size", size)
                                                .build())
                                .retrieve()
                                .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<CustomPageDTO<CourseAggregationDTO>>>() {
                                });
                return coursesMono.flatMap(courseResponse -> {
                        CustomPageDTO<CourseAggregationDTO> coursesPage = courseResponse.getData();
                        List<CourseAggregationDTO> courses = coursesPage.getContent();
                        System.out.println("coursesssssss" + courses);
                        List<Long> userIds = courses.stream()
                                        .map(CourseAggregationDTO::getUserId)
                                        .distinct()
                                        .toList();

                        List<Long> courseIds = courses.stream()
                                        .map(CourseAggregationDTO::getId)
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

                        Mono<List<DiscountDTO>> discountsMono = client.get()
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
                                        .bodyToMono(new ParameterizedTypeReference<List<DiscountDTO>>() {
                                        });

                        return usersMono.zipWith(discountsMono).map(tuple -> {
                                Map<Long, UserDTO> userMap = tuple.getT1().getData().stream()
                                                .collect(Collectors.toMap(UserDTO::getId, u -> u));
                                Map<Long, DiscountDTO> discountMap = tuple.getT2().stream()
                                                .collect(Collectors.toMap(DiscountDTO::getCourseId,
                                                                d -> d));

                                courses.forEach(course -> {
                                        course.setUser(userMap.get(course.getUserId()));
                                        course.setDiscount(discountMap.get(course.getId()));
                                });

                                return new CustomPageDTO<>(courses, coursesPage.getTotalElements(),
                                                coursesPage.getTotalPages(), null);
                        });
                });
        }

        public Mono<CourseAggregationDTO> getCourseDetail(Long courseId) {
                WebClient client = webClientBuilder.build();

                // 1. Lấy thông tin chi tiết của khóa học từ course-service
                Mono<ApiResponseDTO<CourseAggregationDTO>> courseDetailMono = client.get()
                                .uri(uriBuilder -> uriBuilder
                                                .scheme("http")
                                                .host("course-service")
                                                .path("/api/v1/courses/{id}") // Đường dẫn API chi tiết khóa học
                                                .build(courseId)) // Truyền courseId vào path
                                .retrieve()
                                .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<CourseAggregationDTO>>() {
                                });

                // Sử dụng flatMap để xử lý Mono của courseDetail
                return courseDetailMono.flatMap(courseResponse -> {
                        CourseAggregationDTO course = courseResponse.getData();
                        if (course == null) {
                                // Xử lý trường hợp không tìm thấy khóa học
                                return Mono.empty(); // Hoặc Mono.error(new CourseNotFoundException());
                        }

                        // Lấy userId và courseId để gọi các service khác
                        Long userId = course.getUserId();
                        Long currentCourseId = course.getId();

                        // 2. Lấy thông tin người dùng (giảng viên) từ user-service
                        Mono<ApiResponseDTO<List<UserDTO>>> userMono = client.get()
                                        .uri(uriBuilder -> uriBuilder
                                                        .scheme("http")
                                                        .host("user-service")
                                                        .path("/api/v1/users/by-ids")
                                                        .queryParam("ids", userId) // Chỉ cần 1 user id
                                                        .build())
                                        .retrieve()
                                        .bodyToMono(new ParameterizedTypeReference<ApiResponseDTO<List<UserDTO>>>() {
                                        });

                        // 3. Lấy thông tin giảm giá từ discount-service
                        Mono<List<DiscountDTO>> discountMono = client.get()
                                        .uri(uriBuilder -> uriBuilder
                                                        .scheme("http")
                                                        .host("discount-service")
                                                        .path("/api/v1/discounts/get-discout-for-course")
                                                        .queryParam("courseIds", currentCourseId) // Chỉ cần 1 course id
                                                        .build())
                                        .retrieve()
                                        .bodyToMono(new ParameterizedTypeReference<List<DiscountDTO>>() {
                                        });

                        // Kết hợp kết quả từ userMono và discountMono
                        return userMono.zipWith(discountMono)
                                        .map(tuple -> {
                                                List<UserDTO> users = tuple.getT1().getData();
                                                List<DiscountDTO> discounts = tuple.getT2();

                                                // Gán thông tin user (giảng viên) vào khóa học
                                                if (users != null && !users.isEmpty()) {
                                                        course.setUser(users.get(0)); // Lấy user đầu tiên (chính là
                                                                                      // giảng viên)
                                                }

                                                // Gán thông tin giảm giá vào khóa học (nếu có)
                                                if (discounts != null && !discounts.isEmpty()) {
                                                        // Logic để chọn giảm giá phù hợp nhất nếu có nhiều giảm giá cho
                                                        // 1 khóa học
                                                        // Hiện tại, ta chỉ lấy cái đầu tiên tìm thấy
                                                        course.setDiscount(discounts.get(0));
                                                }
                                                return course; // Trả về đối tượng khóa học đã được làm giàu thông tin
                                        })
                                        // Xử lý nếu không tìm thấy user hoặc discount (đặt giá trị mặc định hoặc null)
                                        .defaultIfEmpty(course) // Nếu zipWith không emit gì (ví dụ user/discount
                                                                // service lỗi/trả về rỗng), vẫn trả về course ban đầu
                                        .onErrorResume(e -> {
                                                // Log lỗi và vẫn trả về course ban đầu hoặc Mono.error tùy theo yêu cầu
                                                System.err.println("Error enriching course detail: " + e.getMessage());
                                                return Mono.just(course);
                                        });
                });
        }
}