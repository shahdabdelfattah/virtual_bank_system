package com.vbank.account.repository;

import com.vbank.account.entity.Account;
import com.vbank.account.enums.AccountStatus;
import com.vbank.account.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findByUserId(UUID userId);

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);

    @Query("""
            SELECT a
            FROM Account a
            WHERE a.status = 'ACTIVE'
            AND a.lastTransactionAt < :cutoff
            """)
    List<Account> findInactiveAccounts( @Param("cutoff") LocalDateTime cutoff);

    List<Account> findByStatusAndAccountType(AccountStatus status, AccountType accountType);

    boolean existsByAccountType(AccountType accountType);

    Optional<Account> findByAccountType(AccountType accountType);
}
