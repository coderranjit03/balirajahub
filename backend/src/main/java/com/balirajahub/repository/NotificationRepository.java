package com.balirajahub.repository;

import com.balirajahub.entity.Notification;
import com.balirajahub.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    // ==========================================
    // Get Notifications of Current User (Paginated)
    // ==========================================

    @Query("""
            SELECT n
            FROM Notification n
            WHERE n.user = :user
            ORDER BY n.createdAt DESC
            """)
    Page<Notification> getNotificationsByUser(
            @Param("user") User user,
            Pageable pageable
    );

    // ==========================================
    // Count Unread Notifications
    // ==========================================

    @Query("""
            SELECT COUNT(n)
            FROM Notification n
            WHERE n.user = :user
            AND n.isRead = false
            """)
    Long countUnreadNotifications(
            @Param("user") User user
    );

    // ==========================================
    // Mark All Notifications As Read
    // ==========================================

    @Modifying
    @Transactional
    @Query("""
            UPDATE Notification n
            SET n.isRead = true
            WHERE n.user = :user
            AND n.isRead = false
            """)
    void markAllNotificationsAsRead(
            @Param("user") User user
    );

    // ==========================================
    // Delete All Notifications
    // ==========================================

    @Modifying
    @Transactional
    @Query("""
            DELETE
            FROM Notification n
            WHERE n.user = :user
            """)
    void deleteAllNotifications(
            @Param("user") User user
    );

    // ==========================================
    // Find Notification By Id & User
    // (Ownership Validation)
    // ==========================================

    @Query("""
            SELECT n
            FROM Notification n
            WHERE n.id = :notificationId
            AND n.user = :user
            """)
    Optional<Notification> findByIdAndUser(
            @Param("notificationId") Long notificationId,
            @Param("user") User user
    );
}