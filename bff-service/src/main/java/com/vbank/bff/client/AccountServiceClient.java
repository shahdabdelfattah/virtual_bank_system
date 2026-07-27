package com.vbank.bff.client;

import com.vbank.bff.dto.response.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountServiceClient {

    private final RestClient.Builder restClientBuilder;

    @Value("${account-service.base-url}")
    private String accountServiceUrl;

    public List<AccountResponse> getAccounts(UUID userId) {

        return restClientBuilder.build()
                .get()
                .uri(accountServiceUrl + "/users/{userId}/accounts", userId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}