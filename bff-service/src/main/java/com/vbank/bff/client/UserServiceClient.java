package com.vbank.bff.client;

import com.vbank.bff.dto.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserServiceClient {

    private final RestClient.Builder restClientBuilder;

    @Value("${user-service.base-url}")
    private String baseUrl;

    public UserProfileResponse getProfile(UUID userId) {

        return restClientBuilder.build()
                .get()
                .uri(baseUrl + "/users/{userId}/profile", userId)
                .retrieve()
                .body(UserProfileResponse.class);
    }
}