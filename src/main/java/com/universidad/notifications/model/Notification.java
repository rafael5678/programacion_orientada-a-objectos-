package com.universidad.notifications.model;

import com.universidad.notifications.enums.NotificationChannel;
import com.universidad.notifications.enums.NotificationStatus;
import com.universidad.notifications.enums.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String destinatario;

    @Column(nullable = false)
    private String mensaje;

    private LocalDateTime fechaEnvio;

    @Enumerated(EnumType.STRING)
    private NotificationStatus estado;

    @Enumerated(EnumType.STRING)
    private NotificationType tipo;

    @Enumerated(EnumType.STRING)
    private NotificationChannel canal;
}
