package com.nexusvault.mspayments.service;

import reactor.core.publisher.Mono;
import java.math.BigDecimal;

public interface PaymentCommunicationService {
    Mono<Boolean> sincronizarFondosConBilleteraAsync(Long userId, BigDecimal montoAbonado);
}