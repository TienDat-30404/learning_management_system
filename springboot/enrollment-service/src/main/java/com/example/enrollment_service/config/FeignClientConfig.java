package com.example.enrollment_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignClientConfig implements RequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(FeignClientConfig.class); 

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && !authHeader.isEmpty()) {
                template.header("Authorization", authHeader);
            }
        } 
        else {
            logger.warn("No RequestContext found, unable to add Authorization header.");
        }
    }
}
