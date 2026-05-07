package com.nexusvault.mswallet.controller;

import com.nexusvault.mswallet.model.ModelWallet;
import com.nexusvault.mswallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletRepository walletRepository;

    @GetMapping("/all")
    public List<ModelWallet> getAllWallets() {
        return walletRepository.findAll();
    }
}