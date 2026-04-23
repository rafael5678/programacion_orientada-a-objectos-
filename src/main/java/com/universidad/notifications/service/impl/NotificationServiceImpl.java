package com.universidad.notifications.service.impl;

import com.universidad.notifications.dto.*;
import com.universidad.notifications.model.EmailNotification;
import com.universidad.notifications.model.MobileNotification;
import com.universidad.notifications.model.Notification;
import com.universidad.notifications.model.SmsNotification;
import com.universidad.notifications.enums.NotificationChannel;
import com.universidad.notifications.enums.NotificationStatus;
import com.universidad.notifications.enums.NotificationType;
import com.universidad.notifications.exception.DuplicateCodigoException;
import com.universidad.notifications.exception.NotificationNotFoundException;
import com.universidad.notifications.repository.NotificationRepository;
import com.universidad.notifications.service.NotificationService;
import com.universidad.notifications.service.strategy.NotificationSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final Map<NotificationChannel, NotificationSender> senders;

    public NotificationServiceImpl(NotificationRepository repository, List<NotificationSender> senderList) {
        this.repository = repository;
        this.senders = senderList.stream()
                .collect(Collectors.toMap(NotificationSender::getChannel, Function.identity()));
    }

    @Override
    @Transactional
    public NotificationResponseDTO createEmailNotification(EmailNotificationRequestDTO request) {
        validateCodigo(request.getCodigo());
        EmailNotification notification = EmailNotification.builder()
                .codigo(generateCodigo(request.getCodigo()))
                .destinatario(request.getDestinatario())
                .mensaje(request.getMensaje())
                .tipo(request.getTipo())
                .canal(NotificationChannel.EMAIL)
                .emailDestinatario(request.getEmailDestinatario())
                .asunto(request.getAsunto())
                .ccDestinatarios(request.getCcDestinatarios())
                .estado(NotificationStatus.PENDIENTE)
                .build();
        
        return saveAndSend(notification);
    }

    @Override
    @Transactional
    public NotificationResponseDTO createSmsNotification(SmsNotificationRequestDTO request) {
        validateCodigo(request.getCodigo());
        SmsNotification notification = SmsNotification.builder()
                .codigo(generateCodigo(request.getCodigo()))
                .destinatario(request.getDestinatario())
                .mensaje(request.getMensaje())
                .tipo(request.getTipo())
                .canal(NotificationChannel.SMS)
                .numeroTelefono(request.getNumeroTelefono())
                .codigoOperador(request.getCodigoOperador())
                .estado(NotificationStatus.PENDIENTE)
                .build();
        
        return saveAndSend(notification);
    }

    @Override
    @Transactional
    public NotificationResponseDTO createMobileNotification(MobileNotificationRequestDTO request) {
        validateCodigo(request.getCodigo());
        MobileNotification notification = MobileNotification.builder()
                .codigo(generateCodigo(request.getCodigo()))
                .destinatario(request.getDestinatario())
                .mensaje(request.getMensaje())
                .tipo(request.getTipo())
                .canal(NotificationChannel.MOBILE_APP)
                .deviceToken(request.getDeviceToken())
                .plataforma(request.getPlataforma())
                .badgeCount(request.getBadgeCount())
                .estado(NotificationStatus.PENDIENTE)
                .build();
        
        return saveAndSend(notification);
    }

    @Override
    public List<NotificationResponseDTO> getAllNotifications() {
        return repository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationResponseDTO getNotificationById(Long id) {
        return repository.findById(id)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new NotificationNotFoundException("Notificación no encontrada con ID: " + id));
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsByChannel(NotificationChannel canal) {
        return repository.findByCanal(canal).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsByType(NotificationType tipo) {
        return repository.findByTipo(tipo).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsByStatus(NotificationStatus estado) {
        return repository.findByEstado(estado).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NotificationResponseDTO updateStatus(Long id, EstadoUpdateDTO request) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notificación no encontrada con ID: " + id));
        
        notification.setEstado(request.getEstado());
        Notification updated = repository.save(notification);
        return convertToResponseDTO(updated);
    }

    @Override
    @Transactional
    public void deleteNotification(Long id) {
        if (!repository.existsById(id)) {
            throw new NotificationNotFoundException("Notificación no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }

    private NotificationResponseDTO saveAndSend(Notification notification) {
        Notification saved = repository.save(notification);
        
        try {
            NotificationSender sender = senders.get(saved.getCanal());
            if (sender != null) {
                sender.send(saved);
                saved.setEstado(NotificationStatus.ENVIADO);
                saved.setFechaEnvio(LocalDateTime.now());
                repository.save(saved);
            }
        } catch (Exception e) {
            log.error("Error al enviar notificación: {}", e.getMessage());
            saved.setEstado(NotificationStatus.FALLIDO);
            repository.save(saved);
        }
        
        return convertToResponseDTO(saved);
    }

    private void validateCodigo(String codigo) {
        if (codigo != null && !codigo.isBlank() && repository.existsByCodigo(codigo)) {
            throw new DuplicateCodigoException("El código '" + codigo + "' ya existe");
        }
    }

    private String generateCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
        return codigo;
    }

    private NotificationResponseDTO convertToResponseDTO(Notification notification) {
        return NotificationResponseDTO.builder()
                .id(notification.getId())
                .codigo(notification.getCodigo())
                .destinatario(notification.getDestinatario())
                .mensaje(notification.getMensaje())
                .fechaEnvio(notification.getFechaEnvio())
                .estado(notification.getEstado())
                .tipo(notification.getTipo())
                .canal(notification.getCanal())
                .build();
    }
}
