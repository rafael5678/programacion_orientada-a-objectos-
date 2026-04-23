package com.universidad.notifications.service.strategy;

import com.universidad.notifications.model.MobileNotification;
import com.universidad.notifications.model.Notification;
import com.universidad.notifications.enums.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MobileAppSender implements NotificationSender {
    @Override
    public void send(Notification notification) {
        if (notification instanceof MobileNotification mobileNotification) {
            log.info("Enviando MOBILE_APP a: {} (Plataforma: {}) | Mensaje: {} | Badge: {}", 
                mobileNotification.getDeviceToken(), 
                mobileNotification.getPlataforma(), 
                mobileNotification.getMensaje(),
                mobileNotification.getBadgeCount());
        }
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.MOBILE_APP;
    }
}
