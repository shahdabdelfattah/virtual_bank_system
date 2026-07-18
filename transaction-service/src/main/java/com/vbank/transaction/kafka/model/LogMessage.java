package com.vbank.transaction.kafka.model;

public record LogMessage(
        String message,
        String messageType,
        String dateTime
) {}