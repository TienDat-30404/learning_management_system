package com.example.quiz_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

import java.util.stream.Collectors;

@Configuration
public class FeignClientConfig implements RequestInterceptor {

    @Value("${internal.api.key}")
    private String apiKeyInternal;

    private static final Logger logger = LoggerFactory.getLogger(FeignClientConfig.class);

    @Override
    public void apply(RequestTemplate template) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String userId = authentication.getName();
            String userRoles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            template.header("X-User-ID", userId);
            template.header("API_KEY_INTERNAL", apiKeyInternal);
            template.header("X-User-Roles", userRoles);
            logger.info("Added X-User-ID: {} and X-User-Roles: {} to Feign request.", userId, userRoles);
        } else {
            logger.warn(
                    "No authenticated user in SecurityContext. X-User-ID and X-User-Roles headers not added to Feign request.");
        }
    }
}
