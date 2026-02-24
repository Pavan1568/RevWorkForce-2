package com.revworkforce.service.impl;

import com.revworkforce.entity.Notification;
import com.revworkforce.entity.User;
import com.revworkforce.exception.ResourceNotFoundException;
import com.revworkforce.repository.NotificationRepository;
import com.revworkforce.repository.UserRepository;
import com.revworkforce.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Notification> getAllNotifications(Long userId) {
        return notificationRepository.findByUser_Id(userId);
    }

    @Override
    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUser_IdAndReadStatusFalse(userId);
    }

    @Override
    public Notification createNotification(Long userId, String message) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setReadStatus(false);

        return notificationRepository.save(notification);
    }
}