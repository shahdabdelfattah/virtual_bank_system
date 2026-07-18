package com.vbank.logging.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vbank.logging.entity.LogMessage;
import com.vbank.logging.repository.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaLogConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaLogConsumer.class);
    private final LogRepository logRepository;
    private final ObjectMapper objectMapper;

    public KafkaLogConsumer(LogRepository logRepository, ObjectMapper objectMapper) {
        this.logRepository = logRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${logging.topic}", groupId = "${spring.kafka.consumer.group-id:logging-group}")
    public void listen(String message) {
        log.info("Received log message from Kafka: {}", message);
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            String logContent = jsonNode.has("message") ? jsonNode.get("message").asText() : "";
            String messageType = jsonNode.has("messageType") ? jsonNode.get("messageType").asText() : "";
            String dateTime = jsonNode.has("dateTime") ? jsonNode.get("dateTime").asText() : "";

            LogMessage logMessage = new LogMessage(logContent, messageType, dateTime);
            logRepository.save(logMessage);
            log.info("Successfully saved log message to database.");
        } catch (Exception e) {
            log.error("Failed to parse or save Kafka log message", e);
        }
    }
}
