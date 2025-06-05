package com.example.notification_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "notifications")
@Data
@EqualsAndHashCode(callSuper=true)
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User id cannot be null")
    private Long userId; 

    @NotBlank(message = "Message can not be null")
    private String message;

    @NotNull(message = "IsRead is not null")
    private Boolean isRead;
}
