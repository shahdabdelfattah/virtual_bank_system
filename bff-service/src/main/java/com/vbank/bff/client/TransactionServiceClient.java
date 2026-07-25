package com.vbank.bff.client;

import com.vbank.bff.dto.response.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionServiceClient {

    private final RestClient.Builder restClientBuilder;

    @Value("${transaction-service.base-url}")
    private String transactionServiceUrl;

    public List<TransactionResponse> getTransactions(UUID accountId) {

        return restClientBuilder.build()
                .get()
                .uri(transactionServiceUrl + "/accounts/{accountId}/transactions", accountId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}