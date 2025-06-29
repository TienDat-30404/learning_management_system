package com.example.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ApiKeyFilter apiKeyFilter;

    public SecurityConfig(ApiKeyFilter apiKeyFilter, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.apiKeyFilter = apiKeyFilter;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    // Public endpoints (không cần xác thực)
                    .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
                    .requestMatchers("/error").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/users/by-ids").permitAll()

                    
                    // API Key only endpoints (chỉ cần X-API-Key)
                    // .requestMatchers(HttpMethod.GET, "/api/v1/users/by-ids").hasAuthority("ROLE_INTERNAL")
                    .requestMatchers("/api/v1/users/*/exists").hasAnyAuthority("ROLE_INTERNAL")
                    
                    // JWT + Role based endpoints (cần JWT token và role Admin)
                    .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAuthority("Admin")
                    .requestMatchers(HttpMethod.POST, "/api/v1/users").hasAuthority("Admin")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAuthority("Admin")
                    
                    .requestMatchers(HttpMethod.GET, "/api/v1/roles").hasAuthority("Admin")
                    .requestMatchers(HttpMethod.POST, "/api/v1/roles").hasAuthority("Admin")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/roles/**").hasAuthority("Admin")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/roles/**").hasAuthority("Admin")
                    
                    // Tất cả các request khác cần xác thực
                    .anyRequest().authenticated())
            
            // Thêm các filter theo thứ tự
            .addFilterBefore(this.apiKeyFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}