package com.vbank.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaLogProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public KafkaLogProducer(KafkaTemplate<String, String> kafkaTemplate,
                            @Value("${logging.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendLog(String jsonLog) {
        kafkaTemplate.send(topic, jsonLog);
    }
}
