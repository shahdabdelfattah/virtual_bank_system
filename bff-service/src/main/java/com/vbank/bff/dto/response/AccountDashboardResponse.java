package com.vbank.bff.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record AccountDashboardResponse(

        UUID accountId,
        String accountNumber,
        String accountType,
        BigDecimal balance,
        String status,
        List<TransactionResponse> transactions

) { }