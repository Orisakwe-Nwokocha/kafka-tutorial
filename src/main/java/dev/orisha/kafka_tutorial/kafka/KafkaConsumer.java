//package dev.orisha.kafka_tutorial.kafka;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class KafkaConsumer {
//
//
//    @KafkaListener(topics = {"${orisha.kafka.topic}"})
//    public String listens(final String in) {
//        log.info("Received Message: {}", in);
//        return in;
//    }
//
//}
