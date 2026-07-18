package com.vbank.transaction.client;

import com.vbank.transaction.config.AccountServiceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceClient {

    private final WebClient webClient;

    private final AccountServiceProperties properties;


    //wait for salma
    public void debit(UUID accountId, BigDecimal amount) {
        throw new UnsupportedOperationException("Account Service not implemented yet.");
    }

    public void credit(UUID accountId, BigDecimal amount) {
        throw new UnsupportedOperationException("Account Service not implemented yet.");
    }
}
