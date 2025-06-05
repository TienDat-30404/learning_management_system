package com.example.notification_service.dto.notification;


import lombok.Data;

@Data
public class NotificationResponseDTO {
    private Long id;
    private String message;
    private Boolean isRead;
}
