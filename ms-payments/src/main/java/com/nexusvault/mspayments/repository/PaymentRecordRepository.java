package com.nexusvault.mspayments.repository;

import com.nexusvault.mspayments.model.PaymentRecord;
import com.nexusvault.mspayments.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {

    // Método VITAL: Buscar el pago de una orden específica
    // Como en tu modelo pusiste que orderId es UNIQUE, devolvemos un Optional
    Optional<PaymentRecord> findByOrderId(Long orderId);

    // Método extra: Para que el administrador vea todos los pagos que han fallado
    List<PaymentRecord> findByStatus(PaymentStatus status);
}