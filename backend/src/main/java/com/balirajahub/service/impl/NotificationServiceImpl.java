package com.balirajahub.service.impl;

import com.balirajahub.dto.response.NotificationResponse;
import com.balirajahub.entity.Notification;
import com.balirajahub.entity.User;
import com.balirajahub.entity.enums.NotificationType;
import com.balirajahub.exception.NotificationNotFoundException;
import com.balirajahub.repository.NotificationRepository;
import com.balirajahub.service.AuthenticatedUserService;
import com.balirajahub.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl
        implements NotificationService {

    // ==========================================
    // Dependencies
    // ==========================================

    private final NotificationRepository notificationRepository;

    private final AuthenticatedUserService authenticatedUserService;

    // ==========================================
    // Get Notifications
    // ==========================================

    @Override
    public Page<NotificationResponse> getMyNotifications(
            int page,
            int size) {

        User user = authenticatedUserService.getCurrentUser();

        Pageable pageable = PageRequest.of(
                page,
                size
        );

        return notificationRepository
                .getNotificationsByUser(
                        user,
                        pageable
                )
                .map(this::mapToResponse);
    }

    // ==========================================
    // Mark Notification As Read
    // ==========================================

    @Override
    public void markAsRead(Long notificationId) {

        User user = authenticatedUserService.getCurrentUser();

        Notification notification = notificationRepository
                .findByIdAndUser(notificationId, user)
                .orElseThrow(() ->
                        new NotificationNotFoundException(
                                "Notification not found."));

        notification.setIsRead(true);

        notificationRepository.save(notification);
    }

    // ==========================================
    // Mark All Notifications As Read
    // ==========================================

    @Override
    public void markAllAsRead() {

        User user = authenticatedUserService.getCurrentUser();

        notificationRepository
                .markAllNotificationsAsRead(user);
    }

    // ==========================================
    // Delete Notification
    // ==========================================

    @Override
    public void deleteNotification(Long notificationId) {

        User user = authenticatedUserService.getCurrentUser();

        Notification notification = notificationRepository
                .findByIdAndUser(notificationId, user)
                .orElseThrow(() ->
                        new NotificationNotFoundException(
                                "Notification not found."));

        notificationRepository.delete(notification);
    }

    // ==========================================
    // Delete All Notifications
    // ==========================================

    @Override
    public void deleteAllNotifications() {

        User user = authenticatedUserService.getCurrentUser();

        notificationRepository
                .deleteAllNotifications(user);
    }

    // ==========================================
    // Create Notification
    // ==========================================

    @Override
    public Notification createNotification(
            String title,
            String message,
            NotificationType type) {

        User user = authenticatedUserService.getCurrentUser();

        Notification notification = Notification.builder()
                .title(title)
                .message(message)
                .type(type)
                .user(user)
                .build();

        return notificationRepository.save(notification);
    }

    // ==========================================
    // Get Unread Notification Count
    // ==========================================

    @Override
    public Long getUnreadNotificationCount() {

        User user = authenticatedUserService.getCurrentUser();

        return notificationRepository
                .countUnreadNotifications(user);
    }

    // ==========================================
    // Mapper
    // ==========================================

    private NotificationResponse mapToResponse(
            Notification notification) {

        return NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .type(notification.getType())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}