package com.nexusvault.mswallet.service;

import com.nexusvault.mswallet.model.ModelWallet;
import java.math.BigDecimal;
import java.util.Optional;

public interface WalletService {
    Optional<ModelWallet> getWalletByUserId(Long userId);
    boolean addFunds(Long userId, BigDecimal amount);
    boolean deductFunds(Long userId, BigDecimal amount);
}