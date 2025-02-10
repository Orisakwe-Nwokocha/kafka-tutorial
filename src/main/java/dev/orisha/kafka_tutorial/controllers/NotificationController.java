package dev.orisha.kafka_tutorial.controllers;

import dev.orisha.kafka_tutorial.factory.NotificationServiceFactory;
import dev.orisha.kafka_tutorial.services.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static dev.orisha.kafka_tutorial.utils.constants.ControllerConstants.ERR_MESSAGE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/notify")
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
            Map<Object, String> apiResponse = buildApiResponse(response, OK, recipient);
            return new ResponseEntity<>(apiResponse, OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            var apiResponse = buildApiResponse(ERR_MESSAGE, INTERNAL_SERVER_ERROR, recipient);
            return new ResponseEntity<>(apiResponse, INTERNAL_SERVER_ERROR);
        }
    }

    private Map<Object, String> buildApiResponse(String response, HttpStatus status, String recipient) {
        response = "'%s' %s to %s!".formatted(message, response, recipient);
        Map<Object, String> apiResponse = new HashMap<>();
        apiResponse.put("status", status.toString());
        apiResponse.put("message", response);
        return apiResponse;
    }

}
