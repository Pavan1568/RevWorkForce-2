package com.revworkforce.controller;

import com.revworkforce.entity.Notification;
import com.revworkforce.service.NotificationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // 🔔 Get unread notifications
    @GetMapping("/unread/{managerId}")
    @PreAuthorize("hasRole('MANAGER')")
    public List<Notification> getUnread(@PathVariable Long managerId) {
        return notificationService.getUnreadNotifications(managerId);
    }

    // 🔔 Get all notifications
    @GetMapping("/all/{managerId}")
    @PreAuthorize("hasRole('MANAGER')")
    public List<Notification> getAll(@PathVariable Long managerId) {
        return notificationService.getAllNotifications(managerId);
    }

    // ✅ Mark notification as read
    @PatchMapping("/{notificationId}/read")
    @PreAuthorize("hasRole('MANAGER')")
    public Notification markAsRead(@PathVariable Long notificationId) {
        return notificationService.markAsRead(notificationId);
    }
    // 👤 Employee - Get unread notifications
    @GetMapping("/employee/unread/{userId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<Notification> getEmployeeUnread(@PathVariable Long userId) {
        return notificationService.getUnreadNotifications(userId);
    }

    // 👤 Employee - Get all notifications
    @GetMapping("/employee/all/{userId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<Notification> getEmployeeAll(@PathVariable Long userId) {
        return notificationService.getAllNotifications(userId);
    }

    // 👤 Employee - Mark as read
    @PatchMapping("/employee/{notificationId}/read")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public Notification markEmployeeRead(@PathVariable Long notificationId) {
        return notificationService.markAsRead(notificationId);
    }

}