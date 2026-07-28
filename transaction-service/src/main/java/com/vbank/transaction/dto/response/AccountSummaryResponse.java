package com.vbank.transaction.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountSummaryResponse(
        UUID accountId,
        String accountNumber,
        BigDecimal balance,
        String accountType
) {}