package com.revworkforce.service.impl;

import com.revworkforce.entity.Notification;
import com.revworkforce.exception.ResourceNotFoundException;
import com.revworkforce.repository.NotificationRepository;
import com.revworkforce.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> getUnreadNotifications(Long managerId) {
        return notificationRepository.findByManagerIdAndReadStatusFalse(managerId);
    }

    @Override
    public List<Notification> getAllNotifications(Long managerId) {
        return notificationRepository.findByManagerId(managerId);
    }

    @Override
    public Notification markAsRead(Long notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));

        notification.setReadStatus(true);

        return notificationRepository.save(notification);
    }
}