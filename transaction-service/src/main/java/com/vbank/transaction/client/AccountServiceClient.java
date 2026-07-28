package com.vbank.transaction.client;

import com.vbank.transaction.dto.request.AccountTransferRequest;
import com.vbank.transaction.dto.response.AccountResponse;
import com.vbank.transaction.dto.response.AccountSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
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

    public AccountResponse getSystemAccount() {

        return webClient.get()
                .uri("/system-account")
                .retrieve()
                .bodyToMono(AccountResponse.class)
                .block();
    }

    public List<AccountSummaryResponse> getActiveSavingsAccounts() {

        return webClient.get()
                .uri("/accounts/savings/active")
                .retrieve()
                .bodyToFlux(AccountSummaryResponse.class)
                .collectList()
                .block();
    }
}