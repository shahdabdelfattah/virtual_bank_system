package com.vbank.account.client;

import com.vbank.account.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceClient {

    private final WebClient webClient;

    public void validateUserExists(UUID userId, String authorizationHeader) {

        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new RuntimeException("Authorization header is missing.");
        }

        try {
            webClient.get()
                    .uri("/users/{userId}/profile", userId)
                    .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

        } catch (WebClientResponseException.NotFound ex) {
            throw new ResourceNotFoundException(
                    "User with id " + userId + " not found."
            );
        }
    }
}