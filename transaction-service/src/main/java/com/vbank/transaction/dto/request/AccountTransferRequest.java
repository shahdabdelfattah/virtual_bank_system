package com.vbank.transaction.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountTransferRequest(

        UUID fromAccountId,
        UUID toAccountId,
        BigDecimal amount

) {}