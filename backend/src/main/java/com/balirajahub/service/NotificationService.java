package com.balirajahub.service;

import com.balirajahub.dto.response.NotificationResponse;
import com.balirajahub.entity.Notification;
import com.balirajahub.entity.enums.NotificationType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NotificationService {

    Page<NotificationResponse> getMyNotifications(
            int page,
            int size);

    void markAsRead(Long notificationId);

    void markAllAsRead();

    void deleteNotification(Long notificationId);

    void deleteAllNotifications();

    Notification createNotification(
            String title,
            String message,
            NotificationType type
    );

    Long getUnreadNotificationCount();

}