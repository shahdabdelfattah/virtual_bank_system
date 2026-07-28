package com.vbank.transaction.client;

import com.vbank.transaction.dto.request.AccountTransferRequest;
import com.vbank.transaction.dto.response.AccountResponse;
import com.vbank.transaction.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceClient {

    private final WebClient webClient;

    public void transfer(
            UUID fromAccountId,
            UUID toAccountId,
            BigDecimal amount
    ) {

        AccountTransferRequest request = new AccountTransferRequest(
                fromAccountId,
                toAccountId,
                amount
        );

        webClient.put()
                .uri("/accounts/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public AccountResponse getAccount(UUID accountId) {

        try {
            return webClient.get()
                    .uri("/accounts/{id}", accountId)
                    .retrieve()
                    .bodyToMono(AccountResponse.class)
                    .block();

        } catch (WebClientResponseException.NotFound ex) {
            throw new ResourceNotFoundException("Account not found.");
        }
    }
}