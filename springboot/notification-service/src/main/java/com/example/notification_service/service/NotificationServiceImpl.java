package com.example.notification_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.example.notification_service.dto.CustomPageDTO;
import com.example.notification_service.dto.notification.NotificationRequestDTO;
import com.example.notification_service.dto.notification.NotificationResponseDTO;
import com.example.notification_service.mapper.NotificationMapper;
import com.example.notification_service.model.Notification;
import com.example.notification_service.repository.NotificationRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    // private final UserClient userClient;

    public CustomPageDTO<NotificationResponseDTO> getAllNotificationByUser(Long userId, Pageable pageable) {
        Page<Notification> pageNotification = notificationRepository.findByUserId(userId, pageable);
        Page<NotificationResponseDTO> notifications = pageNotification.map(notificationMapper::toDTO);
        return new CustomPageDTO<>(
                notifications.getContent(), notifications.getTotalElements(), notifications.getTotalPages());
    }

    public void createNotification(NotificationRequestDTO request) {
        System.out.println("request" + request);
        Notification notification = notificationMapper.toEntity(request);
        notificationRepository.save(notification);
    }

    public NotificationResponseDTO updateReadNotification(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found with id = " + id));
        notification.setIsRead(true);
        notificationRepository.save(notification);

        NotificationResponseDTO response = notificationMapper.toDTO(notification);
        return response;
    }
}
