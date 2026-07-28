package com.vbank.bff.service;

import com.vbank.bff.client.AccountServiceClient;
import com.vbank.bff.client.TransactionServiceClient;
import com.vbank.bff.client.UserServiceClient;
import com.vbank.bff.dto.response.AccountDashboardResponse;
import com.vbank.bff.dto.response.DashboardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserServiceClient userServiceClient;
    private final AccountServiceClient accountServiceClient;
    private final TransactionServiceClient transactionServiceClient;

    public DashboardResponse getDashboard(UUID userId) {

        Mono<DashboardResponse> dashboardMono = userServiceClient.getProfile(userId)
                .zipWith(accountServiceClient.getAccounts(userId))
                .flatMap(tuple -> {
                    var user = tuple.getT1();
                    var accounts = tuple.getT2();

                    Mono<List<AccountDashboardResponse>> accountsWithTx =
                            Flux.fromIterable(accounts)
                                    .flatMap(account ->
                                            transactionServiceClient.getTransactions(account.accountId())
                                                    .map(transactions -> new AccountDashboardResponse(
                                                            account.accountId(),
                                                            account.accountNumber(),
                                                            account.accountType(),
                                                            account.balance(),
                                                            account.status(),
                                                            transactions
                                                    ))
                                    )
                                    .collectList();

                    return accountsWithTx.map(dashboardAccounts -> new DashboardResponse(
                            user.userId(),
                            user.username(),
                            user.email(),
                            user.firstName(),
                            user.lastName(),
                            dashboardAccounts
                    ));
                });

        return dashboardMono.block();
    }
}