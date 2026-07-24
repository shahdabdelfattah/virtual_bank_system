package com.vbank.transaction.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TransferExecutionRequest(

        @NotNull
        UUID transactionId
) { }
