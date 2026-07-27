package com.vbank.transaction.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransferExecutionResponse(

        UUID transactionId,

        String status,

        LocalDateTime timestamp
) { }
