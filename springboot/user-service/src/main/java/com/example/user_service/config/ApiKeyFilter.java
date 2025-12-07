package com.example.user_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
@Order(1)
public class ApiKeyFilter extends OncePerRequestFilter {

    private final String validApiKey;

    public ApiKeyFilter(@Value("${internal.api.key}") String validApiKey) {
        this.validApiKey = validApiKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if (shouldCheckApiKey(requestURI)) {
            String apiKey = request.getHeader("API_KEY_INTERNAL");
            System.out.println("fmekejnj");
            System.out.println("apiKEYYYYYYYYYYYYYYYYYYYYYYYY" + apiKey);
            if (apiKey == null || !validApiKey.equals(apiKey)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid API Key");
                return;
            }
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    "internal-service",
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_INTERNAL")));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        System.out.println("nottttttttttttttttttttttttttttttt");
        
        filterChain.doFilter(request, response);
    }

    private boolean shouldCheckApiKey(String requestURI) {
        return requestURI.matches("/api/v1/users/\\d+/exists");
        // ||
        // requestURI.startsWith("/api/v1/users/by-ids");
    }
}
