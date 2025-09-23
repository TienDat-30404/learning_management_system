package com.example.course_service.context;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUser {
    public Long getUserId() {
        String userIdStr = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("nj3w3333333333333333333333333333333333333333333" + userIdStr);
        return Long.parseLong(userIdStr);
    }
}