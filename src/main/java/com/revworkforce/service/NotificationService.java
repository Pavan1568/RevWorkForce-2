package com.revworkforce.service;

import com.revworkforce.entity.Notification;

import java.util.List;

public interface NotificationService {

    List<Notification> getAllNotifications(Long userId);

    List<Notification> getUnreadNotifications(Long userId);

    Notification createNotification(Long userId, String message);
}