package com.vbank.transaction.service;

import com.vbank.transaction.client.AccountServiceClient;
import com.vbank.transaction.dto.request.TransferExecutionRequest;
import com.vbank.transaction.dto.request.TransferInitiationRequest;
import com.vbank.transaction.dto.response.AccountResponse;
import com.vbank.transaction.dto.response.AccountSummaryResponse;
import com.vbank.transaction.dto.response.TransferExecutionResponse;
import com.vbank.transaction.dto.response.TransferInitiationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterestService {

    private final AccountServiceClient accountServiceClient;
    private final TransactionService transactionService;


    public void processDailyInterest() {

        log.info("Running daily interest job...");

        AccountResponse systemAccount = accountServiceClient.getSystemAccount();

        List<AccountSummaryResponse> savingsAccounts = accountServiceClient.getActiveSavingsAccounts();

        log.info("System account: {}", systemAccount.accountNumber());
        log.info("Savings accounts found: {}", savingsAccounts.size());

        for (AccountSummaryResponse account : savingsAccounts) {

            try{
                BigDecimal interest = account.balance().multiply(new BigDecimal("0.05"));

                TransferInitiationRequest request =
                        new TransferInitiationRequest(
                                systemAccount.accountId(),
                                account.accountId(),
                                interest,
                                "Scheduled 5% Interest"
                        );

                TransferInitiationResponse response = transactionService.initiate(request);

                log.info("Created transaction {}", response.transactionId());
                log.info("Account {} receives interest {}", account.accountNumber(), interest);

                TransferExecutionRequest executionRequest = new TransferExecutionRequest(response.transactionId());
                transactionService.execute(executionRequest);
            } catch (Exception e) {
                log.error("Failed to process interest for account {}", account.accountNumber(), e);
            }

        }
    }
}