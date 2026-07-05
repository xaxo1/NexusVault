package com.nexusvault.mswallet.repository;

import com.nexusvault.mswallet.model.ModelWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA destinado al almacenamiento y consulta de la entidad {@link ModelWallet}.
 */
@Repository
public interface WalletRepository extends JpaRepository<ModelWallet, Long> {

    /**
     * Localiza un registro de billetera a partir del identificador de su usuario propietario.
     *
     * @param userId El ID del jugador o dueño de la cuenta.
     * @return Un envoltorio {@link Optional} con el objeto {@link ModelWallet} si es encontrado.
     */
    // Método esencial para buscar la billetera de un usuario específico
    Optional<ModelWallet> findByUserId(Long userId);
}