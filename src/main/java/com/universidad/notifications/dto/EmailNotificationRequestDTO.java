package com.universidad.notifications.dto;

import com.universidad.notifications.enums.NotificationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotificationRequestDTO {
    private String codigo;

    @NotBlank(message = "El destinatario es obligatorio")
    private String destinatario;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    @NotNull(message = "El tipo de notificación es obligatorio")
    private NotificationType tipo;

    @NotBlank(message = "El email del destinatario es obligatorio")
    @Email(message = "Email inválido")
    private String emailDestinatario;

    @NotBlank(message = "El asunto es obligatorio")
    private String asunto;

    private String ccDestinatarios;
}
