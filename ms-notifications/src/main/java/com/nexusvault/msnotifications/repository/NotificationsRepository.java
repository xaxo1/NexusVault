package com.nexusvault.msnotifications.repository;

import com.nexusvault.msnotifications.model.ModelNotifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationsRepository extends JpaRepository<ModelNotifications, Long> {

    // Método útil para buscar notificaciones que aún no se envían
    List<ModelNotifications> findByStatus(String status);
}