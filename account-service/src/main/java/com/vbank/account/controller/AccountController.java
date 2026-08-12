package com.vbank.account.controller;


import com.vbank.account.dto.request.CreateAccountRequestDTO;
import com.vbank.account.dto.request.TransferRequestDTO;
import com.vbank.account.dto.response.AccountResponseDTO;
import com.vbank.account.dto.response.AccountSummaryDTO;
import com.vbank.account.dto.response.CreateAccountResponseDTO;
import com.vbank.account.dto.response.MessageResponseDTO;
import com.vbank.account.kafka.producer.LogProducer;
import com.vbank.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
//@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final LogProducer logProducer;

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAccountResponseDTO createAccount(
            @Valid @RequestBody CreateAccountRequestDTO request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        logProducer.publishRequest(request);

        CreateAccountResponseDTO response = accountService.createAccount(request, authorizationHeader);

        logProducer.publishResponse(response);

        return response;
    }

    @GetMapping("/accounts/savings/active")
    public List<AccountSummaryDTO> getActiveSavingsAccounts() {

        logProducer.publishRequest(Map.of());

        List<AccountSummaryDTO> response =
                accountService.getActiveSavingsAccounts();

        logProducer.publishResponse(response);

        return response;
    }

    @GetMapping("/system-account")
    public AccountResponseDTO getSystemAccount() {

        logProducer.publishRequest(Map.of());

        AccountResponseDTO response =
                accountService.getSystemAccount();

        logProducer.publishResponse(response);

        return response;
    }

    @GetMapping("/accounts/{accountId}")
    public AccountResponseDTO getAccountById(@PathVariable UUID accountId) {

        Map<String, UUID> requestLog = Map.of(
                "accountId", accountId
        );

        logProducer.publishRequest(requestLog);

        AccountResponseDTO response = accountService.getAccountById(accountId);

        logProducer.publishResponse(response);

        return response;
    }

    @GetMapping("/users/{userId}/accounts")
    public List<AccountSummaryDTO> getAccountsByUserId(@PathVariable UUID userId) {

        Map<String, UUID> requestLog = Map.of(
                "userId", userId
        );

        logProducer.publishRequest(requestLog);

        List<AccountSummaryDTO> response = accountService.getAccountsByUserId(userId);

        logProducer.publishResponse(response);

        return response;
    }

    @PutMapping("/accounts/transfer")
    public MessageResponseDTO transferBalance(@Valid @RequestBody TransferRequestDTO request) {

        logProducer.publishRequest(request);

        MessageResponseDTO response =
                accountService.transferBalance(request);

        logProducer.publishResponse(response);

        return response;
    }
}
