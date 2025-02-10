package dev.orisha.kafka_tutorial.factory;

import dev.orisha.kafka_tutorial.services.NotificationService;
import dev.orisha.kafka_tutorial.utils.BeanNameUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Alternative Approach Using a Map Instead of
 * {@link org.springframework.context.ApplicationContext ApplicationContext}
 * <p> Instead of manually retrieving beans from
 * {@link org.springframework.context.ApplicationContext ApplicationContext} like in
 * {@link dev.orisha.kafka_tutorial.factory.NotificationServiceFactory NotificationServiceFactory},
 * you can autowire all NotificationService beans into a Map.
 * The keys will be the bean simple names, and the values will be the
 * corresponding service implementations.
 */

@Component
@Slf4j
public class NotificationServiceRegistry {

    private final Map<String, NotificationService> notificationServices;
    private final NotificationService defaultNotificationService;

    @Autowired
    public NotificationServiceRegistry(List<NotificationService> services, NotificationService defaultNotificationService) {
        this.notificationServices = services.stream()
                .collect(Collectors.toMap(service -> service.getClass().getSimpleName(),
                                          service -> service,
                                          (previous, current) -> previous)
                );
        this.defaultNotificationService = defaultNotificationService;
    }

    public NotificationService getNotificationService(final String notificationType) {
        String simpleName = BeanNameUtil.getNotificationServiceSimpleName(notificationType);
        log.info("Looking up notification service for '{}'", simpleName);

        NotificationService service = notificationServices.get(simpleName);
        if (service != null) {
            log.info("Found notification service: '{}'", simpleName);
            return service;
        }

        log.warn("No specific notification service found for '{}'. Using default Notification service", simpleName);
        return defaultNotificationService;
    }

}
