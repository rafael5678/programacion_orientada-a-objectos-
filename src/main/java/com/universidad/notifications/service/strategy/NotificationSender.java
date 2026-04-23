package com.universidad.notifications.service.strategy;

import com.universidad.notifications.model.Notification;
import com.universidad.notifications.enums.NotificationChannel;

public interface NotificationSender {
    void send(Notification notification);
    NotificationChannel getChannel();
}
