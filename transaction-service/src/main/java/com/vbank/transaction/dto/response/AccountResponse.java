package com.vbank.transaction.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountResponse(
        UUID accountId,
        UUID userId,
        String accountNumber,
        String accountType,
        BigDecimal balance,
        String status
) {}