package com.revworkforce.service;

import com.revworkforce.entity.Notification;

import java.util.List;

public interface NotificationService {

    List<Notification> getUnreadNotifications(Long managerId);

    List<Notification> getAllNotifications(Long managerId);

    Notification markAsRead(Long notificationId);
}