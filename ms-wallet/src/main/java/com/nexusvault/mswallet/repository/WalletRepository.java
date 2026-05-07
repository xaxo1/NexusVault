package com.nexusvault.mswallet.repository;

import com.nexusvault.mswallet.model.ModelWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<ModelWallet, Long> {

    // Método esencial para buscar la billetera de un usuario específico
    Optional<ModelWallet> findByUserId(Long userId);
}