package com.balirajahub.controller;

import com.balirajahub.common.ApiResponse;
import com.balirajahub.dto.response.NotificationResponse;
import com.balirajahub.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // ==========================================
    // Get My Notifications (Paginated)
    // ==========================================

    @GetMapping
    public ApiResponse<Page<NotificationResponse>>
    getMyNotifications(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        return ApiResponse.success(
                "Notifications fetched successfully.",
                notificationService.getMyNotifications(
                        page,
                        size
                )
        );
    }

    // ==========================================
    // Mark Notification As Read
    // ==========================================

    @PatchMapping("/{notificationId}/read")
    public ApiResponse<Void> markAsRead(
            @PathVariable Long notificationId) {

        notificationService.markAsRead(notificationId);

        return ApiResponse.success(
                "Notification marked as read.",
                null
        );
    }

    // ==========================================
    // Mark All Notifications As Read
    // ==========================================

    @PatchMapping("/read-all")
    public ApiResponse<Void> markAllAsRead() {

        notificationService.markAllAsRead();

        return ApiResponse.success(
                "All notifications marked as read.",
                null
        );
    }

    // ==========================================
    // Delete Notification
    // ==========================================

    @DeleteMapping("/{notificationId}")
    public ApiResponse<Void> deleteNotification(
            @PathVariable Long notificationId) {

        notificationService.deleteNotification(notificationId);

        return ApiResponse.success(
                "Notification deleted successfully.",
                null
        );
    }

    // ==========================================
    // Get Unread Notification Count
    // ==========================================

    @GetMapping("/unread-count")
    public ApiResponse<Long> getUnreadNotificationCount() {

        return ApiResponse.success(
                "Unread notification count fetched successfully.",
                notificationService.getUnreadNotificationCount()
        );
    }

    // ==========================================
    // Delete All Notifications
    // ==========================================

    @DeleteMapping
    public ApiResponse<Void> deleteAllNotifications() {

        notificationService.deleteAllNotifications();

        return ApiResponse.success(
                "All notifications deleted successfully.",
                null
        );
    }
}