package com.vbank.account.mapper;

import com.vbank.account.dto.request.CreateAccountRequestDTO;
import com.vbank.account.dto.response.AccountResponseDTO;
import com.vbank.account.dto.response.AccountSummaryDTO;
import com.vbank.account.dto.response.CreateAccountResponseDTO;
import com.vbank.account.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account toEntity(CreateAccountRequestDTO request) {
        Account account = new Account();

        account.setUserId(request.getUserId());
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getInitialBalance());

        return account;
    }

    public CreateAccountResponseDTO toCreateAccountResponse(Account account) {
        return new CreateAccountResponseDTO(
                account.getId(),
                account.getAccountNumber(),
                "Account created successfully."
        );
    }

    public AccountResponseDTO toAccountResponse(Account account) {
        return new AccountResponseDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus()
        );
    }

    public AccountSummaryDTO toAccountSummary(Account account) {
        return new AccountSummaryDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus()
        );
    }
}
