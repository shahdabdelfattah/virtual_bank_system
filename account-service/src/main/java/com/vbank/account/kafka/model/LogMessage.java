package com.vbank.account.kafka.model;

public record LogMessage(
        String message,
        String messageType,
        String dateTime
) {}