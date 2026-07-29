package com.vbank.account.controller;


import com.vbank.account.dto.request.CreateAccountRequestDTO;
import com.vbank.account.dto.request.TransferRequestDTO;
import com.vbank.account.dto.response.AccountResponseDTO;
import com.vbank.account.dto.response.AccountSummaryDTO;
import com.vbank.account.dto.response.CreateAccountResponseDTO;
import com.vbank.account.dto.response.MessageResponseDTO;
import com.vbank.account.kafka.model.LogMessage;
import com.vbank.account.kafka.producer.LoggingProducer;
import com.vbank.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
//@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final LoggingProducer loggingProducer;

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAccountResponseDTO createAccount( @Valid @RequestBody CreateAccountRequestDTO request) {
        return accountService.createAccount(request);
    }

    @GetMapping("/accounts/savings/active")
    public List<AccountSummaryDTO> getActiveSavingsAccounts() {
        return accountService.getActiveSavingsAccounts();
    }

    @GetMapping("/system-account")
    public AccountResponseDTO getSystemAccount() {
        return accountService.getSystemAccount();
    }

    @GetMapping("/accounts/{accountId}")
    public AccountResponseDTO getAccountById( @PathVariable UUID accountId) {
        return accountService.getAccountById(accountId);
    }

    @GetMapping("/users/{userId}/accounts")
    public List<AccountSummaryDTO> getAccountsByUserId( @PathVariable UUID userId) {
        return accountService.getAccountsByUserId(userId);
    }

    @PutMapping("/accounts/transfer")
    public MessageResponseDTO transferBalance( @Valid @RequestBody TransferRequestDTO request) {
        return accountService.transferBalance(request);
    }

    @GetMapping("/test-kafka")
    public String testKafka() {

        LogMessage log = LogMessage.builder()
                .message("Hello from Account Service")
                .messageType("Request")
                .dateTime(LocalDateTime.now())
                .build();

        loggingProducer.send(log);

        return "Message sent";
    }
}
