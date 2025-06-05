package com.example.notification_service.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.notification_service.context.AuthenticatedUser;
import com.example.notification_service.dto.ApiResponseDTO;
import com.example.notification_service.dto.CustomPageDTO;
import com.example.notification_service.dto.notification.NotificationResponseDTO;
import com.example.notification_service.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
@RestController
public class NotificationController {
        private final NotificationService notificationService;
        private final AuthenticatedUser authenticatedUser;

        @GetMapping
        public ResponseEntity<ApiResponseDTO<CustomPageDTO<NotificationResponseDTO>>> getAllNotificationByUser(
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size) {
                Long userId = authenticatedUser.getUserId();
                Pageable pageable = PageRequest.of(page, size);
                CustomPageDTO<NotificationResponseDTO> notifications = notificationService.getAllNotificationByUser(
                                userId,
                                pageable);
                ApiResponseDTO<CustomPageDTO<NotificationResponseDTO>> response = new ApiResponseDTO<>(
                                200, notifications, "List notification of user");
                return ResponseEntity.ok(response);
        }

        @PatchMapping("/{id}")
        public ResponseEntity<ApiResponseDTO<NotificationResponseDTO>> updateReadNotification(
                        @PathVariable Long id) {
                NotificationResponseDTO notification = notificationService.updateReadNotification(id);
                ApiResponseDTO<NotificationResponseDTO> response = new ApiResponseDTO<>(
                                200, notification, "update read notification successfully");
                return ResponseEntity.ok(response);
        }
}
