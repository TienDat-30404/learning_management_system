package com.example.notification_service.dto.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationRequestDTO {
    @NotNull(message = "UserId can not be null")
    private Long userId;

    @NotBlank(message = "message is not empty")
    private String message;

    @NotNull(message = "isRead can not be null")
    private Boolean isRead;

}
