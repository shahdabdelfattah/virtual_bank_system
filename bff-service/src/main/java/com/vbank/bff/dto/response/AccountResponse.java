package com.vbank.bff.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountResponse(

        // wait for salma
        UUID accountId,
        String accountNumber,
        String accountType,
        BigDecimal balance,
        String status

) { }