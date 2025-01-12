//package dev.orisha.kafka_tutorial.kafka;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import dev.orisha.kafka_tutorial.config.KafkaProperties;
//import dev.orisha.kafka_tutorial.domain.CustomerVisitEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//import static java.time.LocalDateTime.now;
//
//@Component
//@Slf4j
//public class KafkaProducer {
//
//    private final KafkaProperties kafkaProperties;
//    private final ObjectMapper objectMapper;
//    private final KafkaTemplate<String, String> kafkaTemplate;
//    private int counter;
//
//    @Autowired
//    public KafkaProducer(KafkaProperties kafkaProperties,
//                         ObjectMapper objectMapper,
//                         KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaProperties = kafkaProperties;
//        this.objectMapper = objectMapper;
//        this.kafkaTemplate = kafkaTemplate;
//        this.counter = 1;
//    }
//
//    @Scheduled(fixedRate = 15000, initialDelay = 10000)
//    public void sendEvents() {
//        CustomerVisitEvent event = CustomerVisitEvent.builder()
//                .customerId(UUID.randomUUID().toString())
//                .dateTime(now())
//                .build();
//        try {
//            final String payload = objectMapper.writeValueAsString(event);
//            log.info("Sending customer visit event: {} with payload: {}", counter, payload);
//            kafkaTemplate.send(kafkaProperties.getTopic(), (payload + counter));
//            ++counter;
//        } catch (JsonProcessingException e) {
//            log.error("Could not serialize event: {}", e.getMessage());
//            throw new RuntimeException(e);
//        } catch (Exception exception) {
//            log.error("Error while sending customer visit event: {}", exception.getMessage());
//        }
//    }
//
//}
