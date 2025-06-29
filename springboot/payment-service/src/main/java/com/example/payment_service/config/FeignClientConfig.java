// package com.example.payment_service.config;

// import feign.RequestInterceptor;
// import feign.RequestTemplate;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.context.request.RequestContextHolder;
// import org.springframework.web.context.request.ServletRequestAttributes;

// import jakarta.servlet.http.HttpServletRequest;

// @Configuration
// public class FeignClientConfig implements RequestInterceptor {

//     @Override
//     public void apply(RequestTemplate template) {
//         ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

//         if (attributes != null) {
//             HttpServletRequest request = attributes.getRequest();

//             // Chỉ forward các header mà API Gateway đã set
//             String userId = request.getHeader("X-User-ID");
//             String userRoles = request.getHeader("X-User-Roles");
//             String authHeader = request.getHeader("Authorization");

//             if (userId != null) {
//                 template.header("X-User-ID", userId);
//             }
//             if (userRoles != null) {
//                 template.header("X-User-Roles", userRoles);
//             }
//             if (authHeader != null) {
//                 template.header("Authorization", authHeader);
//             }
//         }
//     }
// }


package com.example.payment_service.config;

import feign.RequestInterceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Value("${internal.api.key}")
    private String apiKeyInternal;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("API_KEY_INTERNAL", apiKeyInternal);
        };
    }
}