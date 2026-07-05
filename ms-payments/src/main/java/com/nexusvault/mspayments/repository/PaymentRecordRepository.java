package com.nexusvault.mspayments.repository;

import com.nexusvault.mspayments.model.PaymentRecord;
import com.nexusvault.mspayments.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de acceso a datos para la entidad PaymentRecord.
 * Permite buscar los registros de pago por su estado o por la orden a la cual pertenecen.
 */
@Repository
public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {

    /**
     * Busca de manera exclusiva el registro de pago vinculado a una orden.
     *
     * @param orderId Identificador único de la orden original.
     * @return Un objeto Optional con el registro de pago, si se encontrase.
     */
    // Método VITAL: Buscar el pago de una orden específica
    // Como en tu modelo pusiste que orderId es UNIQUE, devolvemos un Optional
    Optional<PaymentRecord> findByOrderId(Long orderId);

    /**
     * Consulta el historial de todos los pagos que compartan un estado particular.
     *
     * @param status Estado de la transacción (ej. FAILED, SUCCESS).
     * @return Lista de todos los pagos bajo dicho estado.
     */
    // Método extra: Para que el administrador vea todos los pagos que han fallado
    List<PaymentRecord> findByStatus(PaymentStatus status);
}