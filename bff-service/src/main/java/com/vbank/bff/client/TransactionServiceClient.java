package com.vbank.bff.client;

import com.vbank.bff.dto.response.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionServiceClient {

    private final WebClient transactionServiceWebClient;

    public Mono<List<TransactionResponse>> getTransactions(UUID accountId) {
        return transactionServiceWebClient.get()
                .uri("/accounts/{accountId}/transactions", accountId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}