package com.vbank.account.service;


import com.vbank.account.dto.request.CreateAccountRequestDTO;
import com.vbank.account.dto.request.TransferRequestDTO;
import com.vbank.account.dto.response.AccountResponseDTO;
import com.vbank.account.dto.response.AccountSummaryDTO;
import com.vbank.account.dto.response.CreateAccountResponseDTO;
import com.vbank.account.dto.response.MessageResponseDTO;
import com.vbank.account.entity.Account;
import com.vbank.account.enums.AccountStatus;
import com.vbank.account.exception.BadRequestException;
import com.vbank.account.exception.ResourceNotFoundException;
import com.vbank.account.mapper.AccountMapper;
import com.vbank.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public CreateAccountResponseDTO createAccount(CreateAccountRequestDTO request) {
        Account account = accountMapper.toEntity(request);
        account.setStatus(AccountStatus.ACTIVE);
        String accountNumber = String.valueOf(
                ThreadLocalRandom.current()
                        .nextLong(1000000000L, 9999999999L));

        account.setAccountNumber(accountNumber);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toCreateAccountResponse(savedAccount);
    }

    public AccountResponseDTO getAccountById(UUID accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        return accountMapper.toAccountResponse(account);
    }

    public List<AccountSummaryDTO> getAccountsByUserId(UUID userId) {

        List<Account> accounts = accountRepository.findByUserId(userId);

        if (accounts.isEmpty()) {
            throw new ResourceNotFoundException("No accounts found for this user.");
        }

        return accounts.stream()
                .map(accountMapper::toAccountSummary)
                .toList();
    }

    @Transactional
    public MessageResponseDTO transferBalance(TransferRequestDTO request) {

        Account fromAccount = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Sender account not found"));

        Account toAccount = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Receiver account not found"));

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new BadRequestException("Insufficient balance");
        }

        if (request.getFromAccountId().equals(request.getToAccountId())) {
            throw new BadRequestException("Cannot transfer to the same account.");
        }

        if (fromAccount.getStatus() != AccountStatus.ACTIVE ||
                toAccount.getStatus() != AccountStatus.ACTIVE) {
            throw new BadRequestException("Transfers are allowed only between active accounts.");
        }

        fromAccount.setBalance(
                fromAccount.getBalance().subtract(request.getAmount())
        );

        toAccount.setBalance(
                toAccount.getBalance().add(request.getAmount())
        );

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        return new MessageResponseDTO("Account updated successfully.");
    }
}
