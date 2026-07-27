package com.vbank.account.scheduler;

import com.vbank.account.entity.Account;
import com.vbank.account.enums.AccountStatus;
import com.vbank.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountScheduler {

    private final AccountRepository accountRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void inactivateStaleAccounts() {

        LocalDateTime cutoff = LocalDateTime.now().minusHours(24);
        List<Account> accounts = accountRepository.findInactiveAccounts(cutoff);
        for (Account account : accounts) {

            account.setStatus(AccountStatus.INACTIVE);

        }

        accountRepository.saveAll(accounts);
    }
}
