package dev.orisha.kafka_tutorial.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import static dev.orisha.kafka_tutorial.utils.constants.ServiceConstants.NOTIFICATION_SUCCESS_MESSAGE;

@Service
@Slf4j
@Primary
public class EmailNotificationService implements NotificationService {

    @Override
    public String sendNotificationTo(final String recipient, final String message) {
        log.info("Sending message '{}' to {}", message, recipient);
        String serviceName = this.getClass().getSimpleName();
        return NOTIFICATION_SUCCESS_MESSAGE.formatted(serviceName);
    }

}
