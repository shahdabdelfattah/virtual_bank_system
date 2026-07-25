package com.vbank.account.controller;


import com.vbank.account.dto.request.CreateAccountRequestDTO;
import com.vbank.account.dto.response.AccountResponseDTO;
import com.vbank.account.dto.response.AccountSummaryDTO;
import com.vbank.account.dto.response.CreateAccountResponseDTO;
import com.vbank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
//@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAccountResponseDTO createAccount( @RequestBody CreateAccountRequestDTO request) {
        return accountService.createAccount(request);
    }

    @GetMapping("/accounts/{accountId}")
    public AccountResponseDTO getAccountById( @PathVariable UUID accountId) {
        return accountService.getAccountById(accountId);
    }

    @GetMapping("/users/{userId}/accounts")
    public List<AccountSummaryDTO> getAccountsByUserId( @PathVariable UUID userId) {
        return accountService.getAccountsByUserId(userId);
    }
}
