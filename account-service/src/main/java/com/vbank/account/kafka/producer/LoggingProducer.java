package com.vbank.account.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vbank.account.kafka.model.LogMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoggingProducer {

    @Value("${logging-service.kafka-topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void send(LogMessage logMessage) {

        try {

            String json = objectMapper.writeValueAsString(logMessage);

            kafkaTemplate.send(topic, json);

        } catch (JsonProcessingException e) {

            throw new RuntimeException("Failed to serialize log message", e);

        }

    }
}
