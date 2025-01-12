package dev.orisha.kafka_tutorial.services;

public interface NotificationService {

    String sendNotificationTo(String recipient, String message);

}
