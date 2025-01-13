package dev.orisha.kafka_tutorial.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static dev.orisha.kafka_tutorial.utils.constants.ServiceConstants.NOTIFICATION_SUCCESS_MESSAGE;

@Service
@Slf4j
public class SmsNotificationService implements NotificationService {

    @Override
    public String sendNotificationTo(String recipient, String message) {
        log.info("Sending message '{}' to {}", message, recipient);
        String serviceName = this.getClass().getSimpleName();
        return NOTIFICATION_SUCCESS_MESSAGE.formatted(serviceName);
    }

}
