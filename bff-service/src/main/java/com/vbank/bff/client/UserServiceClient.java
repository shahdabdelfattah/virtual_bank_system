package com.vbank.bff.client;

import com.vbank.bff.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserServiceClient {

    private final WebClient userServiceWebClient;

    public Mono<UserProfileResponse> getProfile(UUID userId) {
        return userServiceWebClient.get()
                .uri("/users/{userId}/profile", userId)
                .retrieve()
                .bodyToMono(UserProfileResponse.class);
    }
}