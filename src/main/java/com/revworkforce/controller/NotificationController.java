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

    @GetMapping("/all/{userId}")
    @PreAuthorize("hasAnyRole('MANAGER','EMPLOYEE')")
    public List<Notification> getAll(@PathVariable Long userId) {
        return notificationService.getAllNotifications(userId);
    }

    @GetMapping("/unread/{userId}")
    @PreAuthorize("hasAnyRole('MANAGER','EMPLOYEE')")
    public List<Notification> getUnread(@PathVariable Long userId) {
        return notificationService.getUnreadNotifications(userId);
    }
}