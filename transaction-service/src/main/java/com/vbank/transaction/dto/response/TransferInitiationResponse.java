package com.vbank.transaction.dto.response;

import com.vbank.transaction.enums.TransactionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransferInitiationResponse(

        UUID transactionId,

        String status,

        LocalDateTime timestamp
) { }
