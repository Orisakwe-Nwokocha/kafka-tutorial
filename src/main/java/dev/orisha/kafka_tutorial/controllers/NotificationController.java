package dev.orisha.kafka_tutorial.controllers;

import dev.orisha.kafka_tutorial.factory.NotificationServiceFactory;
import dev.orisha.kafka_tutorial.services.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/notify")
@RefreshScope
@Slf4j
public class NotificationController {

    @Value("${message}")
    private String message;

    @Value("${notification.type}")
    private String notificationType;

    private final NotificationServiceFactory factory;

    @Autowired
    public NotificationController(final NotificationServiceFactory factory) {
        this.factory = factory;
    }

    @PostMapping
    public ResponseEntity<?> notify(@RequestParam String recipient) {
        NotificationService notificationService = this.factory.getNotificationService(notificationType);
        try {
            String serviceName = notificationService.getClass().getSimpleName();
            log.info("REST request to notify: {} using <{}>", recipient, serviceName);
            String response = notificationService.sendNotificationTo(recipient, message);
            Map<Object, String> apiResponse = buildApiResponse(response, recipient);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Map<Object, String> buildApiResponse(String response, String recipient) {
        response = "'%s' %s to %s!".formatted(message, response, recipient);
        Map<Object, String> apiResponse = new HashMap<>();
        apiResponse.put("status", HttpStatus.OK.toString());
        apiResponse.put("message", response);
        return apiResponse;
    }

}
