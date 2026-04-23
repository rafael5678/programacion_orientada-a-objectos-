package com.universidad.notifications.config;

import com.universidad.notifications.model.EmailNotification;
import com.universidad.notifications.model.MobileNotification;
import com.universidad.notifications.model.SmsNotification;
import com.universidad.notifications.enums.NotificationChannel;
import com.universidad.notifications.enums.NotificationStatus;
import com.universidad.notifications.enums.NotificationType;
import com.universidad.notifications.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final NotificationRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            log.info("Inicializando datos de prueba...");

            // 2 Email Notifications
            EmailNotification e1 = EmailNotification.builder()
                    .codigo("E001")
                    .destinatario("Juan Perez")
                    .mensaje("Tus calificaciones han sido publicadas")
                    .tipo(NotificationType.PUBLICATION_CALIFICACIONES)
                    .canal(NotificationChannel.EMAIL)
                    .emailDestinatario("juan.perez@universidad.edu")
                    .asunto("Calificaciones Disponibles")
                    .estado(NotificationStatus.ENVIADO)
                    .fechaEnvio(LocalDateTime.now())
                    .build();

            EmailNotification e2 = EmailNotification.builder()
                    .codigo("E002")
                    .destinatario("Maria Lopez")
                    .mensaje("Recordatorio de pago de matricula")
                    .tipo(NotificationType.RECORDATORIO_PAGO_MATRICULA)
                    .canal(NotificationChannel.EMAIL)
                    .emailDestinatario("maria.lopez@universidad.edu")
                    .asunto("Pago de Matricula")
                    .estado(NotificationStatus.PENDIENTE)
                    .build();

            // 2 SMS Notifications
            SmsNotification s1 = SmsNotification.builder()
                    .codigo("S001")
                    .destinatario("Carlos Ruiz")
                    .mensaje("Clase de Matematicas cancelada hoy")
                    .tipo(NotificationType.AVISO_CANCELACION_CLASE)
                    .canal(NotificationChannel.SMS)
                    .numeroTelefono("+573001234567")
                    .codigoOperador("MOVISTAR")
                    .estado(NotificationStatus.ENVIADO)
                    .fechaEnvio(LocalDateTime.now())
                    .build();

            SmsNotification s2 = SmsNotification.builder()
                    .codigo("S002")
                    .destinatario("Ana Garcia")
                    .mensaje("Confirmacion inscripcion a evento cultural")
                    .tipo(NotificationType.CONFIRMACION_INSCRIPCION_EVENTO)
                    .canal(NotificationChannel.SMS)
                    .numeroTelefono("+573109876543")
                    .codigoOperador("CLARO")
                    .estado(NotificationStatus.PENDIENTE)
                    .build();

            // 2 Mobile Notifications
            MobileNotification m1 = MobileNotification.builder()
                    .codigo("M001")
                    .destinatario("Luis Diaz")
                    .mensaje("Tus notas estan listas")
                    .tipo(NotificationType.PUBLICATION_CALIFICACIONES)
                    .canal(NotificationChannel.MOBILE_APP)
                    .deviceToken("tok_123456")
                    .plataforma("ANDROID")
                    .badgeCount(1)
                    .estado(NotificationStatus.ENVIADO)
                    .fechaEnvio(LocalDateTime.now())
                    .build();

            MobileNotification m2 = MobileNotification.builder()
                    .codigo("M002")
                    .destinatario("Sofia Mart")
                    .mensaje("Recordatorio: Clase cancelada")
                    .tipo(NotificationType.AVISO_CANCELACION_CLASE)
                    .canal(NotificationChannel.MOBILE_APP)
                    .deviceToken("tok_789012")
                    .plataforma("IOS")
                    .badgeCount(2)
                    .estado(NotificationStatus.PENDIENTE)
                    .build();

            repository.saveAll(List.of(e1, e2, s1, s2, m1, m2));
            log.info("Datos de prueba insertados exitosamente.");
        }
    }
}
