package com.universidad.notifications.repository;

import com.universidad.notifications.model.Notification;
import com.universidad.notifications.enums.NotificationChannel;
import com.universidad.notifications.enums.NotificationStatus;
import com.universidad.notifications.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByCodigo(String codigo);
    List<Notification> findByCanal(NotificationChannel canal);
    List<Notification> findByTipo(NotificationType tipo);
    List<Notification> findByEstado(NotificationStatus estado);
    boolean existsByCodigo(String codigo);
}
