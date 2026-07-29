package com.vbank.account.kafka.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogMessage {
    private String message;

    private String messageType;

    private LocalDateTime dateTime;
}
