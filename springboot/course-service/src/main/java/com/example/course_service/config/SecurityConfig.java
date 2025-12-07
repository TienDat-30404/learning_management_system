package com.example.course_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/v1/lessons?courseId=40").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/v1/categories/**").hasAnyAuthority("Admin", "Manager")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/categories/**").hasAnyAuthority("Admin", "Manager")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/**").hasAnyAuthority("Admin", "Manager")

                        .requestMatchers(HttpMethod.GET, "api/v1/courses/courses-by-user").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/courses/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/courses/**").hasAnyAuthority("Admin", "Manager", "Teacher")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/courses/**").hasAnyAuthority("Admin", "Manager", "Teacher")

                        .requestMatchers(HttpMethod.GET, "/api/v1/lessons/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/lessons/**").hasAnyAuthority("Admin", "Manager", "Teacher")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/lessons/**").hasAnyAuthority("Admin", "Manager")

                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
