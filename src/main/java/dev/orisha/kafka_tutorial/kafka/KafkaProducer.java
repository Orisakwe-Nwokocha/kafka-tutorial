package dev.orisha.kafka_tutorial.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.orisha.kafka_tutorial.config.KafkaConfig;
import dev.orisha.kafka_tutorial.domain.CustomerVisitEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.time.LocalDateTime.now;

@Component
@Slf4j
public class KafkaProducer {

    private final KafkaConfig kafkaConfig;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private int counter;

    public KafkaProducer(KafkaConfig kafkaConfig,
                         ObjectMapper objectMapper,
                         KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaConfig = kafkaConfig;
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.counter = 1;
    }

    @Scheduled(fixedRate = 15000, initialDelay = 10000)
    public void sendEvents() {
        CustomerVisitEvent event = CustomerVisitEvent.builder()
                .customerId(UUID.randomUUID().toString())
                .dateTime(now())
                .build();
        try {
            final String payload = objectMapper.writeValueAsString(event);
            log.info("Sending customer visit event: {} with payload: {}", counter, payload);
            kafkaTemplate.send(kafkaConfig.getTopic(), (payload + counter));
            ++counter;
        } catch (JsonProcessingException e) {
            log.info("Could not serialize event: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
