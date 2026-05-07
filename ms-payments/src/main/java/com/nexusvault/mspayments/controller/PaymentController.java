package com.nexusvault.mspayments.controller;

import com.nexusvault.mspayments.model.PaymentRecord;
import com.nexusvault.mspayments.repository.PaymentRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentRecordRepository paymentRepository;

    @GetMapping
    public List<PaymentRecord> getAllPayments() {
        return paymentRepository.findAll();
    }
}