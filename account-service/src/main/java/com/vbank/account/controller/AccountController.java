package com.vbank.account.controller;


import com.vbank.account.dto.request.CreateAccountRequestDTO;
import com.vbank.account.dto.response.CreateAccountResponseDTO;
import com.vbank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAccountResponseDTO createAccount( @RequestBody CreateAccountRequestDTO request) {
        return accountService.createAccount(request);
    }
}
