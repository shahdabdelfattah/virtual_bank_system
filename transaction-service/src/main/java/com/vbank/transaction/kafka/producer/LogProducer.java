package com.vbank.transaction.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vbank.transaction.kafka.model.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${logging-service.kafka-topic}")
    private String topic;

    public void publishRequest(Object request) {
        publish(request, "Request");
    }

    public void publishResponse(Object response) {
        publish(response, "Response");
    }

    private void publish(Object obj, String messageType) {

        try {

            String jsonPayload = objectMapper.writeValueAsString(obj);

            LogMessage logMessage = new LogMessage(
                    jsonPayload,
                    messageType,
                    LocalDateTime.now().toString()
            );

            String kafkaMessage =
                    objectMapper.writeValueAsString(logMessage);

            kafkaTemplate.send(topic, kafkaMessage);

        } catch (Exception e) {
            log.error("Failed to publish log message", e);
        }
    }
}