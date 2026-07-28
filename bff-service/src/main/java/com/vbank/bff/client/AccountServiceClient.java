package com.vbank.bff.client;

import com.vbank.bff.dto.response.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountServiceClient {

    private final WebClient accountServiceWebClient;

    public Mono<List<AccountResponse>> getAccounts(UUID userId) {
        return accountServiceWebClient.get()
                .uri("/users/{userId}/accounts", userId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}