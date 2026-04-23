package com.universidad.notifications.service;

import com.universidad.notifications.dto.*;
import com.universidad.notifications.enums.NotificationChannel;
import com.universidad.notifications.enums.NotificationStatus;
import com.universidad.notifications.enums.NotificationType;

import java.util.List;

public interface NotificationService {
    NotificationResponseDTO createEmailNotification(EmailNotificationRequestDTO request);
    NotificationResponseDTO createSmsNotification(SmsNotificationRequestDTO request);
    NotificationResponseDTO createMobileNotification(MobileNotificationRequestDTO request);
    
    List<NotificationResponseDTO> getAllNotifications();
    NotificationResponseDTO getNotificationById(Long id);
    List<NotificationResponseDTO> getNotificationsByChannel(NotificationChannel canal);
    List<NotificationResponseDTO> getNotificationsByType(NotificationType tipo);
    List<NotificationResponseDTO> getNotificationsByStatus(NotificationStatus estado);
    
    NotificationResponseDTO updateStatus(Long id, EstadoUpdateDTO request);
    void deleteNotification(Long id);
}
