package com.example.notification_service.service;

import org.springframework.data.domain.Pageable;

import com.example.notification_service.dto.CustomPageDTO;
import com.example.notification_service.dto.notification.NotificationRequestDTO;
import com.example.notification_service.dto.notification.NotificationResponseDTO;

public interface NotificationService {
    public void createNotification(NotificationRequestDTO request);

    public CustomPageDTO<NotificationResponseDTO> getAllNotificationByUser(Long userId, Pageable pageable);
    public NotificationResponseDTO updateReadNotification(Long id);
}
