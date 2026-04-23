package com.universidad.notifications.service.strategy;

import com.universidad.notifications.model.Notification;
import com.universidad.notifications.model.SmsNotification;
import com.universidad.notifications.enums.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SmsSender implements NotificationSender {
    @Override
    public void send(Notification notification) {
        if (notification instanceof SmsNotification smsNotification) {
            log.info("Enviando SMS a: {} (Operador: {}) | Mensaje: {}", 
                smsNotification.getNumeroTelefono(), 
                smsNotification.getCodigoOperador(), 
                smsNotification.getMensaje());
        }
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.SMS;
    }
}
