package com.universidad.notifications.service.strategy;

import com.universidad.notifications.model.EmailNotification;
import com.universidad.notifications.model.Notification;
import com.universidad.notifications.enums.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailSender implements NotificationSender {
    @Override
    public void send(Notification notification) {
        if (notification instanceof EmailNotification emailNotification) {
            log.info("Enviando EMAIL a: {} | Asunto: {} | Mensaje: {}", 
                emailNotification.getEmailDestinatario(), 
                emailNotification.getAsunto(), 
                emailNotification.getMensaje());
        }
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.EMAIL;
    }
}
