package com.vbank.bff.service;

import com.vbank.bff.client.AccountServiceClient;
import com.vbank.bff.client.TransactionServiceClient;
import com.vbank.bff.client.UserServiceClient;
import com.vbank.bff.dto.response.DashboardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserServiceClient userServiceClient;
    private final AccountServiceClient accountServiceClient;
    private final TransactionServiceClient transactionServiceClient;

    public DashboardResponse getDashboard(UUID userId) {

        var user = userServiceClient.getProfile(userId);

        return new DashboardResponse(
                user.userId(),
                user.username(),
                user.email(),
                user.firstName(),
                user.lastName(),
                // NOT Complete wait for account service (salma)
                List.of()
        );
    }

    // NOT Complete wait for account service (salma)
}