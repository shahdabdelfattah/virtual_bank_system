package com.vbank.account.service;


import com.vbank.account.dto.request.CreateAccountRequestDTO;
import com.vbank.account.dto.request.TransferRequestDTO;
import com.vbank.account.dto.response.AccountResponseDTO;
import com.vbank.account.dto.response.AccountSummaryDTO;
import com.vbank.account.dto.response.CreateAccountResponseDTO;
import com.vbank.account.dto.response.MessageResponseDTO;
import com.vbank.account.entity.Account;
import com.vbank.account.enums.AccountStatus;
import com.vbank.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public CreateAccountResponseDTO createAccount(CreateAccountRequestDTO request) {
        Account account = new Account();

        account.setUserId(request.getUserId());
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getInitialBalance());
        account.setStatus(AccountStatus.ACTIVE);
        String accountNumber = String.valueOf(
                ThreadLocalRandom.current()
                        .nextLong(1000000000L, 9999999999L));

        account.setAccountNumber(accountNumber);

        Account savedAccount = accountRepository.save(account);

        return new CreateAccountResponseDTO(
                savedAccount.getId(),
                savedAccount.getAccountNumber(),
                "Account created successfully."
        );
    }

    public AccountResponseDTO getAccountById(UUID accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return new AccountResponseDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus()
        );
    }

    public List<AccountSummaryDTO> getAccountsByUserId(UUID userId) {

        List<Account> accounts = accountRepository.findByUserId(userId);

        if (accounts.isEmpty()) {
            throw new RuntimeException("No accounts found for this user.");
        }

        return accounts.stream()
                .map(account -> new AccountSummaryDTO(
                        account.getId(),
                        account.getAccountNumber(),
                        account.getAccountType(),
                        account.getBalance(),
                        account.getStatus()
                ))
                .toList();
    }
//
//    public MessageResponseDTO transferBalance(TransferRequestDTO request) {
//
//    }
}
