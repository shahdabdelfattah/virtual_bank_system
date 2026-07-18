package com.vbank.transaction.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionHistoryResponse(
    UUID transactionId,
    UUID fromAccountId,
    UUID toAccountId,
    BigDecimal amount,
    String description,
    LocalDateTime timestamp,
    String deliveryStatus

) { }
