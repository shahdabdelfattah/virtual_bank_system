package com.vbank.logging.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dump_logs")
@Getter
@Setter
@NoArgsConstructor
public class LogMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String message;

    private String messageType;

    private String dateTime;

    public LogMessage(String message, String messageType, String dateTime) {
        this.message = message;
        this.messageType = messageType;
        this.dateTime = dateTime;
    }
}
