package com.universidad.notifications.dto;

import com.universidad.notifications.enums.NotificationChannel;
import com.universidad.notifications.enums.NotificationStatus;
import com.universidad.notifications.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDTO {
    private Long id;
    private String codigo;
    private String destinatario;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private NotificationStatus estado;
    private NotificationType tipo;
    private NotificationChannel canal;
    
    // Additional fields for specific types can be handled by subclasses or a flat structure.
    // Given the requirement "campos base + canal + tipo", I'll stick to base for now.
}
