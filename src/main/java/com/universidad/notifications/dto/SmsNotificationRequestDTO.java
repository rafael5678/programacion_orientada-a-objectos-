package com.universidad.notifications.dto;

import com.universidad.notifications.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsNotificationRequestDTO {
    private String codigo;

    @NotBlank(message = "El destinatario es obligatorio")
    private String destinatario;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    @NotNull(message = "El tipo de notificación es obligatorio")
    private NotificationType tipo;

    @NotBlank(message = "El número de teléfono es obligatorio")
    private String numeroTelefono;

    @NotBlank(message = "El código de operador es obligatorio")
    private String codigoOperador;
}
