package com.nexusvault.msnotifications.repository;

import com.nexusvault.msnotifications.model.ModelNotifications;
import com.nexusvault.msnotifications.model.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de acceso a datos para gestionar los registros de las notificaciones.
 * Proporciona métodos para interactuar con la tabla de bitácora en la base de datos.
 */
@Repository
public interface NotificationsRepository extends JpaRepository<ModelNotifications, Long> {

    /**
     * Busca las notificaciones filtrando por su estado transaccional.
     *
     * @param status El estado de notificación a buscar.
     * @return Lista de notificaciones que coinciden con el estado especificado.
     */
    // Método útil para buscar notificaciones que aún no se envían
    List<ModelNotifications> findByStatus(NotificationStatus status);
}