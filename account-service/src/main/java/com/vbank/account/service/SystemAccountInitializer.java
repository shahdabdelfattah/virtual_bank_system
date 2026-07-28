package com.vbank.account.service;

import com.vbank.account.entity.Account;
import com.vbank.account.enums.AccountStatus;
import com.vbank.account.enums.AccountType;
import com.vbank.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SystemAccountInitializer implements CommandLineRunner {

    private final AccountRepository repository;

    @Override
    public void run(String... args) {

        if (repository.existsByAccountType(AccountType.SYSTEM)) {
            return;
        }

        Account account = new Account();

        account.setUserId(UUID.randomUUID());
        account.setAccountNumber("0000000000");
        account.setAccountType(AccountType.SYSTEM);
        account.setBalance(new BigDecimal("999999999999.99"));
        account.setStatus(AccountStatus.ACTIVE);
        account.setLastTransactionAt(LocalDateTime.now());

        repository.save(account);
    }
}
