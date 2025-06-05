package com.example.notification_service.mapper;

import org.springframework.stereotype.Component;

import com.example.notification_service.dto.notification.NotificationRequestDTO;
import com.example.notification_service.dto.notification.NotificationResponseDTO;
import com.example.notification_service.model.Notification;

@Component
public class NotificationMapper {
    public NotificationResponseDTO toDTO(Notification notification) {
        NotificationResponseDTO dto = new NotificationResponseDTO();
        dto.setId(notification.getId());
        dto.setMessage(notification.getMessage());
        dto.setIsRead(notification.getIsRead());
        return dto;
    }

    public Notification toEntity(NotificationRequestDTO request) {
        Notification notification = new Notification();
        notification.setMessage(request.getMessage());
        notification.setIsRead(request.getIsRead());
        notification.setUserId(request.getUserId());
        return notification;
    }
}
