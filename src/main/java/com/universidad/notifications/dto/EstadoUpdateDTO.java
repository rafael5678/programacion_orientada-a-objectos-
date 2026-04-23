package com.universidad.notifications.dto;

import com.universidad.notifications.enums.NotificationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoUpdateDTO {
    @NotNull(message = "El estado es obligatorio")
    private NotificationStatus estado;
}
