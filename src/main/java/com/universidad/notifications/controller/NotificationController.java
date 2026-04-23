package com.universidad.notifications.controller;

import com.universidad.notifications.dto.*;
import com.universidad.notifications.enums.NotificationChannel;
import com.universidad.notifications.enums.NotificationStatus;
import com.universidad.notifications.enums.NotificationType;
import com.universidad.notifications.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService service;

    @PostMapping("/email")
    public ResponseEntity<NotificationResponseDTO> createEmailNotification(@Valid @RequestBody EmailNotificationRequestDTO request) {
        return new ResponseEntity<>(service.createEmailNotification(request), HttpStatus.CREATED);
    }

    @PostMapping("/sms")
    public ResponseEntity<NotificationResponseDTO> createSmsNotification(@Valid @RequestBody SmsNotificationRequestDTO request) {
        return new ResponseEntity<>(service.createSmsNotification(request), HttpStatus.CREATED);
    }

    @PostMapping("/mobile")
    public ResponseEntity<NotificationResponseDTO> createMobileNotification(@Valid @RequestBody MobileNotificationRequestDTO request) {
        return new ResponseEntity<>(service.createMobileNotification(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponseDTO>> getAllNotifications() {
        return ResponseEntity.ok(service.getAllNotifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDTO> getNotificationById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getNotificationById(id));
    }

    @GetMapping("/canal/{canal}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByChannel(@PathVariable NotificationChannel canal) {
        return ResponseEntity.ok(service.getNotificationsByChannel(canal));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByType(@PathVariable NotificationType tipo) {
        return ResponseEntity.ok(service.getNotificationsByType(tipo));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByStatus(@PathVariable NotificationStatus estado) {
        return ResponseEntity.ok(service.getNotificationsByStatus(estado));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<NotificationResponseDTO> updateStatus(@PathVariable Long id, @Valid @RequestBody EstadoUpdateDTO request) {
        return ResponseEntity.ok(service.updateStatus(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        service.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
